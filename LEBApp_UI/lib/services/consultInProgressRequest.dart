import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:lebapp_ui/models/CreateRequestDTO.dart';
import 'dart:async';

import 'expandRequest.dart';

//General Area User Main Page
// ignore: camel_case_types
class ConsultInProgressRequest extends StatefulWidget {

  String firstName;
  int userID;
  String token;

  ConsultInProgressRequest(this.firstName, this.userID,this.token);

  @override
  _ConsultInProgressRequestState createState() => _ConsultInProgressRequestState(firstName,userID,token);
}

class _ConsultInProgressRequestState extends State<ConsultInProgressRequest> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  //final List<String> alphabets = <String>['A', 'B', 'C'];

  String firstName;
  int userID;
  String token;

  _ConsultInProgressRequestState(this.firstName,this.userID,this.token);

  List<CreateRequestDTO> _listFinal = [];
  void _receiveListReq() async{
    final resultList = await fetchRequests(userID.toString(),token);
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
          title:Text("Consult In-Progress Request")
      ),
      body: ListView(
        children: [
          for (int count in List.generate(_listFinal.length, (index) => index + 1))
            ListTile(
              title: Text('Product --> '+ _listFinal[count-1].productName),
              leading: Icon(Icons.add),
              trailing: Text("0$count"),
              onTap: (){
                Navigator.of(context).push(MaterialPageRoute(builder: (context)=>ExpandRequest(firstName, userID,token,_listFinal[count-1])));
              },
            ),
          SizedBox(height: 15.0),
          confirmRequestButton,
        ],
      ),
    );
  }
}

Future<List<CreateRequestDTO>> fetchRequests(String userID, String token) async {

  print(userID);
  print(token);
  String profile = "";
  var url = Uri.parse('http://ec2-54-209-6-238.compute-1.amazonaws.com:8080/api/requests/'+ userID.toString()+'/'+profile);

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
