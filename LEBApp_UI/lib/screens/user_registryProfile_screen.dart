import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:lebapp_ui/models/User.dart';
import 'package:lebapp_ui/screens/user_registryProfile_DeliveryMan.dart';
import 'package:lebapp_ui/screens/user_registryProfile_Point.dart';
import 'package:lebapp_ui/screens/user_registryProfile_Producer.dart';
import 'package:lebapp_ui/screens/user_registryProfile_Transporter.dart';
import 'package:lebapp_ui/screens/user_registry_screenCredent.dart';
import 'package:lebapp_ui/models/User.dart';
import '../main.dart';

// ignore: camel_case_types
class User_RegistryProfile_Screen extends StatefulWidget {

  final User uTest;
  User_RegistryProfile_Screen({Key key, this.uTest}) : super(key: key);

  @override
  _User_RegistryProfile_ScreenState createState() => _User_RegistryProfile_ScreenState();
}

class _User_RegistryProfile_ScreenState extends State<User_RegistryProfile_Screen> {

  bool valuefirst = false;
  bool valuesecond = false;
  bool valuethird = false;
  bool valuefourth = false;

  @override
    Widget build(BuildContext context) {

    final nextButon = Material(
      child: TextButton(
          child: Text('Finalize Info'),
          style: TextButton.styleFrom(
            alignment: Alignment.bottomCenter,
            primary: Colors.white,
            backgroundColor: Colors.teal,
            onSurface: Colors.grey,
          ),
          onPressed: () {
            User uAux = new User(null, null, widget.uTest.firstName, widget.uTest.lastName, null,
                null, true, null, 'x', widget.uTest.createdDate, null, widget.uTest.lastModifiedDate, widget.uTest.phoneNumber, null, widget.uTest.nif,
                widget.uTest.birthday, widget.uTest.address, widget.uTest.isTransporter, widget.uTest.favouriteTransport, widget.uTest.isProducer,widget.uTest.linkSocial, widget.uTest.isPoint, null, widget.uTest.isDeliveryMan, null);

            Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_Registry_ScreenCredent(uTest: uAux)));
            //print(' Object USER: ${widget.uTest.toString()} was passed');
          }
      ),
    );

    return Scaffold(
      appBar: AppBar(
        title: Text("Your User Profile"),
        backgroundColor: Colors.teal,
      ),
      body: Container(
          padding: new EdgeInsets.all(22.0),
          child: Column(
            children: <Widget>[
              SizedBox(width: 10,),
              Text('Choose one or more profiles:',style: TextStyle(fontSize: 20.0), ),
              CheckboxListTile(
                secondary: const Icon(Icons.add),
                title: const Text('Producer'),
                subtitle: Text('Producer subtext'),
                value: this.valuefirst,
                onChanged: (bool value) {
                  setState(() {
                    this.valuefirst = value;
                    print(valuefirst);
                    widget.uTest.isProducer = valuefirst;
                    extraInfoProducerDialog();
                  });
                },
              ),
              CheckboxListTile(
                controlAffinity: ListTileControlAffinity.trailing,
                secondary: const Icon(Icons.add),
                title: const Text('Transporter'),
                subtitle: Text('Transporter subtext'),
                value: this.valuesecond,
                onChanged: (bool value) {
                  setState(() {
                    this.valuesecond = value;
                    print(valuesecond);
                    widget.uTest.isTransporter = valuesecond;
                    extraInfoTransporterDialog();
                  });
                },
              ),
              CheckboxListTile(
                controlAffinity: ListTileControlAffinity.trailing,
                secondary: const Icon(Icons.add),
                title: const Text('Distributor'),
                subtitle: Text('Distributor subtext'),
                value: this.valuethird,
                onChanged: (bool value) {
                  setState(() {
                    this.valuethird = value;
                    print(valuethird);
                    widget.uTest.isDeliveryMan = valuethird;
                    extraInfoDistributorDialogStartHour();
                    extraInfoDistributorDialogEndHour();
                  });
                },
              ),
              CheckboxListTile(
                controlAffinity: ListTileControlAffinity.trailing,
                secondary: const Icon(Icons.add),
                title: const Text('Point'),
                subtitle: Text('Point subtext'),
                value: this.valuefourth,
                onChanged: (bool value) {
                  setState(() {
                    this.valuefourth = value;
                    print(valuefourth);
                    widget.uTest.isPoint = valuefourth;
                    extraInfoPointDialogStartHour();
                    extraInfoPointDialogEndHour();
                  });
                },
              ),
              nextButon,
              SizedBox(
                height: 15.0,
              ),
            ],
          )
      ),
    );
  }

  extraInfoProducerDialog(){

    TextEditingController _textFieldController = TextEditingController();

    return showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('Social link'),
            content: TextField(
              controller: _textFieldController,
              textInputAction: TextInputAction.go,
              keyboardType: TextInputType.numberWithOptions(),
              decoration: InputDecoration(hintText: "Enter your social link"),
            ),
            actions: <Widget>[
              new FlatButton(
                child: new Text('Submit'),
                onPressed: () {
                  Navigator.of(context).pop();
                  widget.uTest.linkSocial = _textFieldController.text;
                },
              )
            ],
          );
        });
  }

  extraInfoTransporterDialog(){

    TextEditingController _textFieldController = TextEditingController();

    return showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('Favourite Transport'),
            content: TextField(
              controller: _textFieldController,
              textInputAction: TextInputAction.go,
              keyboardType: TextInputType.numberWithOptions(),
              decoration: InputDecoration(hintText: "Enter your favourite transport"),
            ),
            actions: <Widget>[
              new FlatButton(
                child: new Text('Submit'),
                onPressed: () {
                  Navigator.of(context).pop();
                  widget.uTest.favouriteTransport = _textFieldController.text;
                },
              )
            ],
          );
        });

  }

  extraInfoDistributorDialogStartHour(){

    TextEditingController birthdayController = TextEditingController();

    return showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('Working Time - Start'),
            content: TextFormField(
              obscureText: false,
              decoration: InputDecoration(hintText: 'Enter start time',icon: Icon(Icons.calendar_today),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
              controller: birthdayController,
              onTap: () async{
                FocusScope.of(context).requestFocus(new FocusNode());

                final TimeOfDay picked=await showTimePicker(context: context,initialTime: TimeOfDay(hour: 5,minute: 10));

                birthdayController.text = picked.format(context);},
            ),
            actions: <Widget>[
              new FlatButton(
                child: new Text('Submit'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              )
            ],
          );
        });
  }

  extraInfoDistributorDialogEndHour(){

    TextEditingController birthdayController = TextEditingController();

    return showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('Working Time - End'),
            content: TextFormField(
              obscureText: false,
              decoration: InputDecoration(hintText: 'Enter end time',icon: Icon(Icons.calendar_today),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
              controller: birthdayController,
              onTap: () async{
                FocusScope.of(context).requestFocus(new FocusNode());

                final TimeOfDay picked=await showTimePicker(context: context,initialTime: TimeOfDay(hour: 5,minute: 10));

                birthdayController.text = picked.format(context);},
            ),
            actions: <Widget>[
              new FlatButton(
                child: new Text('Submit'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              )
            ],
          );
        });
  }

  extraInfoPointDialogStartHour(){

    TextEditingController birthdayController = TextEditingController();

    return showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('Open Time - Start'),
            content: TextFormField(
              obscureText: false,
              decoration: InputDecoration(hintText: 'Enter open time',icon: Icon(Icons.lock_clock),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
              controller: birthdayController,
              onTap: () async{
                FocusScope.of(context).requestFocus(new FocusNode());

                final TimeOfDay picked=await showTimePicker(context: context,initialTime: TimeOfDay(hour: 5,minute: 10));

                birthdayController.text = picked.format(context);},
            ),
            actions: <Widget>[
              new FlatButton(
                child: new Text('Submit'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              )
            ],
          );
        });
  }

  extraInfoPointDialogEndHour(){

    TextEditingController birthdayController = TextEditingController();

    return showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('Close Time - Start'),
            content: TextFormField(
              obscureText: false,
              decoration: InputDecoration(hintText: 'Enter close time',icon: Icon(Icons.lock_clock),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
              controller: birthdayController,
              onTap: () async{
                FocusScope.of(context).requestFocus(new FocusNode());

                final TimeOfDay picked=await showTimePicker(context: context,initialTime: TimeOfDay(hour: 5,minute: 10));

                birthdayController.text = picked.format(context);},
            ),
            actions: <Widget>[
              new FlatButton(
                child: new Text('Submit'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              )
            ],
          );
        });
  }
}