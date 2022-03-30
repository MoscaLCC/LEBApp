import 'dart:convert';
import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:http/http.dart' as http;
import 'package:lebapp_ui/models/CreateRequestDTO.dart';
import 'package:lebapp_ui/models/PointDTO.dart';
import 'package:lebapp_ui/screens/user_main_area.dart';

//General Area User Main Page
// ignore: camel_case_types
class AddPoint extends StatefulWidget {

  String firstName;
  int userID;
  String token;

  AddPoint(this.firstName, this.userID,this.token);

  @override
  _AddPointState createState() => _AddPointState(firstName,userID,token);
}

class _AddPointState extends State<AddPoint> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  String firstName;
  int userID;
  String token;

  _AddPointState(this.firstName,this.userID,this.token);

  // key to form validator
  final _keyForm = GlobalKey<FormState>(); // Our created key

  // Controllers
  final pointNameController = TextEditingController();
  final openTimeController = TextEditingController();
  final closeTimeController = TextEditingController();
  final addressController = TextEditingController();

  void _saveForm(){
    if(_keyForm.currentState.validate()){
      _keyForm.currentState.save();
    }
  }

  @override
  Widget build(BuildContext context) {

    final pointName = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Point Name',icon: Icon(Icons.people),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: pointNameController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a name';
        return null;
      },
    );

    final formatOpen = DateFormat("HH:mm");
    final openTime = DateTimeField(
      format: formatOpen,
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Open Time',icon: Icon(Icons.calendar_today),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: openTimeController,
      onShowPicker: (context, currentValue) async {
        final time = await showTimePicker(
          context: context,
          initialTime: TimeOfDay.fromDateTime(currentValue ?? DateTime.now()),
        );

        print(currentValue);
        openTimeController.text = DateTimeField.convert(time).toString();
        return DateTimeField.convert(time);
      },
    );

    final formatClose = DateFormat("HH:mm");
    final closeTime = DateTimeField(
      format: formatClose,
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Close Time',icon: Icon(Icons.calendar_today),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: closeTimeController,
      onShowPicker: (context, currentValue) async {
        final time = await showTimePicker(
          context: context,
          initialTime: TimeOfDay.fromDateTime(currentValue ?? DateTime.now()),
        );

        print(currentValue);
        closeTimeController.text = DateTimeField.convert(time).toString();
        return DateTimeField.convert(time);
      },
    );

    final address = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Point Address',icon: Icon(Icons.point_of_sale),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: addressController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a address';
        return null;
      },
    );

    final createPointButton = Material(
      child: TextButton(
        child: Text('Create'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () async {
          //button action
          _saveForm();

          print(" ## Send object - Add Point ## ");

          PointDTO addPoint = new PointDTO(null, pointNameController.text,
                openTimeController.text, closeTimeController.text, addressController.text,
                null, null);

          var url = Uri.parse('http://ec2-54-209-6-238.compute-1.amazonaws.com:8080/api/points');
          var body = json.encode(addPoint.toJson());
          print(body);

          Map<String,String> headers = {
            'Content-type' : 'application/json',
            'Accept': 'application/json',
            'Authorization' : token,
          };

          var response = await http.post(url, body: body, headers: headers); // send
          print(response.statusCode);
          if(response.statusCode == 200 || response.statusCode == 201 || response.statusCode == 204){

            showAlertDialog(context, "Success", "Point Addedd");
            Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_Main_Area(firstName, userID, token)));
            return response;

          }else{
            showAlertDialog(context, "Error", "Add Point failed. Please try later");
            return response;
          }
        },
      ),
    );

    return Scaffold(
      appBar: AppBar(
          title: Text('Add Point'),
          backgroundColor: Colors.teal),
      body: SingleChildScrollView(
        child: Form(
          key: _keyForm,
          child: Center(
            child: Container(
              color: Colors.white,
              child: Padding(
                padding: const EdgeInsets.all(36.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    SizedBox(height: 30.0),
                    pointName,
                    SizedBox(height: 30.0),
                    openTime,
                    SizedBox(height: 30.0),
                    closeTime,
                    SizedBox(height: 30.0),
                    address,
                    createPointButton,
                    SizedBox(
                      height: 15.0,
                    ),
                  ],
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}

String formatISOTime(DateTime date) {
  //converts date into the following format:
// or 2019-06-04T12:08:56.235-0700
  var duration = date.timeZoneOffset;
  if (duration.isNegative)
    return (DateFormat("yyyy-MM-ddTHH:mm:ss.mmm").format(date) +
        "z");
  else
    return (DateFormat("yyyy-MM-ddTHH:mm:ss.mmm").format(date) +
        "z");
}

showAlertDialog(BuildContext context, String title, String content) {

  // set up the button
  Widget okButton = TextButton(
    child: Text("OK"),
    onPressed: () {
      Navigator.of(context).pop();
    },
  );

  // set up the AlertDialog
  AlertDialog alert = AlertDialog(
    title: Text(title),
    content: Text(content),
    actions: [
      okButton,
    ],
  );

  // show the dialog
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return alert;
    },
  );
}