import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:lebapp_ui/models/CreateRequestDTO.dart';
import 'package:lebapp_ui/models/RequestCriteriaDTO.dart';
import 'dart:async';

import 'expandRequest.dart';

//General Area User Main Page
// ignore: camel_case_types
class ConsultAvailableRequest extends StatefulWidget {

  String firstName;
  int userID;
  String token;

  ConsultAvailableRequest(this.firstName, this.userID,this.token);

  @override
  _ConsultAvailableRequestState createState() => _ConsultAvailableRequestState(firstName,userID,token);
}

class _ConsultAvailableRequestState extends State<ConsultAvailableRequest> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  //final List<String> alphabets = <String>['A', 'B', 'C'];

  String firstName;
  int userID;
  String token;

  _ConsultAvailableRequestState(this.firstName,this.userID,this.token);

  List<CreateRequestDTO> _listFinal = [];
  void _receiveListReq() async{
    final resultList = await fetchRequests(userID.toString(),token,_selected);
    setState(() => _listFinal = resultList);
  }

  void initState() {
    super.initState();
    WidgetsBinding.instance
        .addPostFrameCallback((_) => _receiveListReq());
  }

  var _selected ="Available";

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

    final filterOptionButton = Material(
      child: TextButton(
        child: Text('Choose filter'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
          //button action to alert dialog box
          _asyncSimpleDialog(context);
        },
      ),
    );

    return Scaffold(
      appBar: AppBar(
          title:Text("Requests & Jobs")
      ),
      body: ListView(
        children: [
          for (int count in List.generate(_listFinal.length, (index) => index + 1))
            ListTile(
              title: Text('Product --> '+ _listFinal[count-1].productName),
              leading: Icon(Icons.add),
              trailing: Text("0$count"),
              onTap: () async {
                await Navigator.of(context).push(MaterialPageRoute(builder: (context)=>ExpandRequest(firstName, userID,token,_listFinal[count-1])));
                _receiveListReq();
              },
            ),
          SizedBox(height: 15.0),
          confirmRequestButton,
          SizedBox(height: 15.0),
          filterOptionButton,
        ],

      ),
    );
  }
   _asyncSimpleDialog(BuildContext context) async {
     _selected  =  await showDialog(
        context: context,
        barrierDismissible: true,
        builder: (BuildContext context) {
          return SimpleDialog(
            title: const Text('Select your option '),
            children: <Widget>[
              SimpleDialogOption(
                onPressed: () {
                  Navigator.pop(context, "Available");
                },
                child: const Text('Available'),
              ),
              SimpleDialogOption(
                onPressed: () {
                  Navigator.pop(context, "My jobs In-Progress");
                },
                child: const Text('My jobs In-Progress'),
              ),
              SimpleDialogOption(
                onPressed: () {
                  Navigator.pop(context, "My requests In-Progress");
                },
                child: const Text('My requests In-Progress'),
              ),
              SimpleDialogOption(
                onPressed: () {
                  Navigator.pop(context, "My jobs history");
                },
                child: const Text('My jobs history'),
              ),
              SimpleDialogOption(
                onPressed: () {
                  Navigator.pop(context, "My requests history");
                },
                child: const Text('My requests history'),
              ),
            ],
          );
        });

     if(_selected != null)
     {
       setState(() {
         _selected = _selected;
       });
     }
  }

}

Future<List<CreateRequestDTO>> fetchRequests(String userID, String token, String option) async {

  print(userID);
  print(token);
  print(option);

  var url = Uri.parse('http://ec2-54-209-6-238.compute-1.amazonaws.com:8080/api/requests');
  var body ="";

  if(option == "Available"){ // Available
    RequestCriteriaDTO newCriteria = RequestCriteriaDTO(null,"WAITING_COLLECTION",null); //int.parse(userID)
    print('## RequestCriteriaDTO:' + newCriteria.toString());
    body = json.encode(newCriteria.toJson());

  }else if(option == "My jobs In-Progress"){ // My jobs In-Progress
    RequestCriteriaDTO newCriteria = RequestCriteriaDTO(null,"OPENED",int.parse(userID)); //int.parse(userID)
    print('## RequestCriteriaDTO:' + newCriteria.toString());
    body = json.encode(newCriteria.toJson());

  }else if(option == "My jobs history"){ // My jobs history
    RequestCriteriaDTO newCriteria = RequestCriteriaDTO(null,"CLOSED",int.parse(userID)); //int.parse(userID)
    print('## RequestCriteriaDTO:' + newCriteria.toString());
    body = json.encode(newCriteria.toJson());
  }

  else if(option == "My requests In-Progress"){ // My request In-progress
    RequestCriteriaDTO newCriteria = RequestCriteriaDTO(int.parse(userID),"OPENED",null); //int.parse(userID)
    print('## RequestCriteriaDTO:' + newCriteria.toString());
    body = json.encode(newCriteria.toJson());
  }

  else if(option == "My requests history"){ // My request In-progress
    RequestCriteriaDTO newCriteria = RequestCriteriaDTO(int.parse(userID),"CLOSED",null); //int.parse(userID)
    print('## RequestCriteriaDTO:' + newCriteria.toString());
    body = json.encode(newCriteria.toJson());
  }

  Map<String,String> headers = {
    'Content-type' : 'application/json',
    'Accept': 'application/json',
    'Authorization' : token,
  };

  List<CreateRequestDTO> listReq;
  final response = await http.post(url, body: body, headers: headers);
  listReq=(json.decode(response.body) as List).map((i) =>
      CreateRequestDTO.fromJson(i)).toList();

  print(listReq.toString());

  return listReq;
}

