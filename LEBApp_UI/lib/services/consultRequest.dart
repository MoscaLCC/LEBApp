import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:lebapp_ui/models/CreateRequestDTO.dart';
import 'dart:async';

import 'expandRequest.dart';

//General Area User Main Page
// ignore: camel_case_types
class ConsultRequest extends StatefulWidget {

  String firstName;
  int userID;
  String profile;
  String token;

  ConsultRequest(this.firstName, this.userID,this.profile,this.token);

  @override
  _ConsultRequestState createState() => _ConsultRequestState(firstName,userID,profile,token);
}

class _ConsultRequestState extends State<ConsultRequest> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  final List<String> alphabets = <String>['A', 'B', 'C'];

  String firstName;
  int userID;
  String profile;
  String token;

  _ConsultRequestState(this.firstName,this.userID,this.profile,this.token);

  List<CreateRequestDTO> _listFinal = [];
  void _receiveListReq() async{
    final resultList = await fetchRequests(userID.toString(), profile,token);
    setState(() => _listFinal = resultList);
  }

  @override
  Widget build(BuildContext context) {

    final confirmRequestButton = Material(
      child: TextButton(
        child: Text('Get'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
          _receiveListReq();
          print(_listFinal);
        },
      ),
    );

    return Scaffold(
      appBar: AppBar(
          title:Text("Consult Request")
      ),
      body: ListView(
        children: [
          for (int count in List.generate(_listFinal.length, (index) => index + 1))
            ListTile(
              title: Text('Product --> '+ _listFinal[count-1].productName),
              leading: Icon(Icons.add),
              trailing: Text("0$count"),
              onTap: (){
                Navigator.of(context).push(MaterialPageRoute(builder: (context)=>ExpandRequest(firstName, userID, profile,token,_listFinal[count-1])));
              },
            ),
          SizedBox(height: 15.0),
          confirmRequestButton,
        ],
      ),
    );
  }
}

Future<List<CreateRequestDTO>> fetchRequests(String userID, String profile, String token) async {

  print(userID);
  print(profile);
  print(token);

  var url = Uri.parse('http://192.168.1.110:8080/api/requests/'+ userID.toString()+'/'+profile);

  Map<String,String> headers = {
    'Content-type' : 'application/json',
    'Accept': 'application/json',
    'Authorization' : token,
  };

  List<CreateRequestDTO> listReq;
  final response = await http.get(url,headers: headers);
  listReq=(json.decode(response.body) as List).map((i) =>
      CreateRequestDTO.fromJson(i)).toList();

  print(listReq.toString());

  return listReq;
}
