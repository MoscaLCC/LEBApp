import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:lebapp_ui/models/CreateRequestDTO.dart';
import 'package:lebapp_ui/models/DimensionsDTO.dart';
import 'package:lebapp_ui/models/RidePathDTO.dart';
import '../main.dart';

//General Area User Main Page
// ignore: camel_case_types
class CreateRequest extends StatefulWidget {

  String firstName;
  int userID;
  String profile;
  String token;

  CreateRequest(this.firstName, this.userID,this.profile,this.token);

  @override
  _CreateRequestState createState() => _CreateRequestState(firstName,userID,profile,token);
}

class _CreateRequestState extends State<CreateRequest> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  String firstName;
  int userID;
  String profile;
  String token;

  _CreateRequestState(this.firstName,this.userID,this.profile,this.token);

  // Controllers
  final productNameController = TextEditingController();
  final productValueController = TextEditingController();
  final descriptionController = TextEditingController();
  final specialCharController = TextEditingController();
  final sourceController = TextEditingController();
  final destinationController = TextEditingController();

  @override
  Widget build(BuildContext context) {

    final productName = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Product Name',icon: Icon(Icons.people),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: productNameController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a name';
        return null;
      },
    );

    final productValue = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Product Value',icon: Icon(Icons.people),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: productValueController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    final description = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Product Description',icon: Icon(Icons.people),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: descriptionController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    final specialChar = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Product Special Characteristics',icon: Icon(Icons.people),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: specialCharController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    final source = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'source',icon: Icon(Icons.people),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: sourceController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );
    final destination = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Destination',icon: Icon(Icons.people),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: destinationController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );
    final confirmRequestButton = Material(
      child: TextButton(
        child: Text('Confirm'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
          //button action

          print(" ## Send object - Create Request ## ");

          DimensionsDTO dim = new DimensionsDTO(500.0, 400.0, 80.0);
          RidePathDTO ride = new RidePathDTO("Guimarães", "Braga", "16", "20");
          CreateRequestDTO newReq = new CreateRequestDTO(29.0, "Caixa Bolos", "Guimarães", "Braga", "916585452", DateTime.now().toString(), DateTime.now().toString(), "Bolos secos", "tem bolor", 5, "OPENED", DateTime.now().toString(), "20", 0.75, 0.0, dim, ride, null);

          var url = Uri.parse('http://192.168.1.110:8080/api/requests/'+ userID.toString());
          var body = json.encode(newReq.toJson());
          print(body);

          Map<String,String> headers = {
            'Content-type' : 'application/json',
            'Accept': 'application/json',
            'Authorization' : token,
          };

          final response = http.post(url, body: body, headers: headers);
          final responseJson = response;
          print(responseJson);
          return response;

          // test request creation

        },
      ),
    );
    return Scaffold(
      appBar: AppBar(
          title: Text('Create new request'),
          backgroundColor: Colors.teal),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            confirmRequestButton,
            SizedBox(
              height: 15.0,
            )
          ],
        ),
      ),
    );
  }
}