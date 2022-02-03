import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:lebapp_ui/models/User.dart';
import 'package:lebapp_ui/models/UserInfo.dart';
import 'package:lebapp_ui/screens/user_edit_info.dart';
import 'package:lebapp_ui/services/addPoint.dart';
import 'package:lebapp_ui/services/consultAvailableRequest.dart';
import 'package:lebapp_ui/services/consultInProgressRequest.dart';
import 'package:lebapp_ui/services/createRequest.dart';
import 'package:http/http.dart' as http;

//General Area User Main Page
// ignore: camel_case_types
class User_Main_Area extends StatefulWidget {

  String firstName;
  int userID;
  String token;

  User_Main_Area(this.firstName, this.userID,this.token);

  @override
  _User_Main_AreaState createState() => _User_Main_AreaState(firstName,userID,token);
}

class _User_Main_AreaState extends State<User_Main_Area> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  String firstName;
  int userID;
  String token;

  _User_Main_AreaState(this.firstName,this.userID,this.token);

  UserInfo userInfoAux = new UserInfo.toDefault();
  void _getReceiveUserInfo() async{
    final resultUserInfo = await getUserInfoDTO(userID,token);
    setState(() => userInfoAux = resultUserInfo);
  }

  void initState() {
    super.initState();
    WidgetsBinding.instance
        .addPostFrameCallback((_) => _getReceiveUserInfo());
  }

  @override
  Widget build(BuildContext context) {

    final personalAreaButton = Material(
      child: TextButton(
        child: Text('Your Personal Info'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () async {
          await Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_Edit_Info(firstName,userID,token,userInfoAux)));
          _getReceiveUserInfo();
        },
      ),
    );

    final availableReqButton = Material(
      child: TextButton(
        child: Text('View Requests & Jobs'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () async {
          //button action
          await Navigator.of(context).push(MaterialPageRoute(builder: (context)=>ConsultAvailableRequest(firstName,userID,token)));
          _getReceiveUserInfo();
        },
      ),
    );

    final createReqButton = Material(
      child: TextButton(
        child: Text('Create Own Request'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () async {
          //button action
          await Navigator.of(context).push(MaterialPageRoute(builder: (context)=>CreateRequest(firstName,userID,token, userInfoAux)));
          _getReceiveUserInfo();
        },
      ),
    );

    final addPointButton = Material(
      child: TextButton(
        child: Text('Add Point'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () async {
          //button action
          await Navigator.of(context).push(MaterialPageRoute(builder: (context)=>AddPoint(firstName,userID,token)));
          _getReceiveUserInfo();
        },
      ),
    );

    //--------------------------------------------------------------------------
    // -------------------------------------------------------------------------

    return Scaffold(
        appBar: AppBar(
            title: Text('Hello '+firstName+' ! '),
            backgroundColor: Colors.teal),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            buildProfileImage(),
            Text(
              ' Available Balance: ' + userInfoAux.availableBalance.toString(),
            ),
            Text(
              ' Pending Balance: ' + userInfoAux.frozenBalance.toString(),
            ),
            personalAreaButton,
            SizedBox(height: 15.0,),
            availableReqButton,
            SizedBox(height: 15.0,),
            createReqButton,
            SizedBox(height: 15.0,),
            addPointButton,
            SizedBox(height: 15.0,),
          ],
        ),
      ),
    );
  }

  Widget buildProfileImage() => CircleAvatar(
    radius: 144 / 2,
    backgroundColor: Colors.grey,
    child: Image.network('https://ksarquitetos.com.br/wp-content/uploads/2014/12/user.png'),
  );

}

Future<UserInfo> getUserInfoDTO(int userID, String token) async {

  print("User");
  var url = Uri.parse('http://ec2-54-209-6-238.compute-1.amazonaws.com:8080/api/user-infos/user/'+userID.toString());
  var body ="";

  Map<String,String> headers = {
    'Content-type' : 'application/json',
    'Accept': 'application/json',
    'Authorization' : token,
  };

  final response = await http.get(url, headers: headers);
  var jsonResponse = json.decode(response.body);

  // parse response in login DTO
  UserInfo userInfoDTO = new UserInfo.fromJson(jsonResponse);

  print(" ## Response:");
  print(userInfoDTO);

  print("ID:" + userInfoDTO.id.toString());
  print("First Name:" + userInfoDTO.firstName);
  print("Available balance:" + userInfoDTO.availableBalance.toString());

  return userInfoDTO;
}