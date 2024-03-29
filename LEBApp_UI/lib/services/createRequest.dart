import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:http/http.dart' as http;
import 'package:lebapp_ui/models/CreateRequestDTO.dart';
import 'package:lebapp_ui/models/UserInfo.dart';
import 'package:lebapp_ui/screens/user_main_area.dart';

//General Area User Main Page
// ignore: camel_case_types
class CreateRequest extends StatefulWidget {

  String firstName;
  int userID;
  String token;
  UserInfo userInfoAux;

  CreateRequest(this.firstName, this.userID,this.token, this.userInfoAux);

  @override
  _CreateRequestState createState() => _CreateRequestState(firstName,userID,token, userInfoAux);
}

class _CreateRequestState extends State<CreateRequest> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  String firstName;
  int userID;
  String token;
  UserInfo userInfoAux;

  _CreateRequestState(this.firstName,this.userID,this.token, this.userInfoAux);

  // key to form validator
  final _keyForm = GlobalKey<FormState>(); // Our created key

  // Controllers
  final productNameController = TextEditingController();
  final productValueController = TextEditingController();
  final descriptionController = TextEditingController();
  final specialCharController = TextEditingController();
  final sourceController = TextEditingController();
  final destinationController = TextEditingController();
  final destinationContactMobileController = TextEditingController();
  final destinationContactEmailController = TextEditingController();
  final initDateController = TextEditingController();
  final expirationDateController = TextEditingController();
  final productHeightController = TextEditingController();
  final productWidthController = TextEditingController();
  final productWeightController = TextEditingController();

  void _saveForm(){
    if(_keyForm.currentState.validate()){
      _keyForm.currentState.save();
    }
  }

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
      decoration: InputDecoration(hintText: 'Product Value',icon: Icon(Icons.money),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: productValueController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    final productHeight = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Height',icon: Icon(Icons.height),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      keyboardType: TextInputType.number,
      controller: productHeightController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    final productWidth = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Width',icon: Icon(Icons.height),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      keyboardType: TextInputType.number,
      controller: productWidthController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    final productWeight = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Weight',icon: Icon(Icons.height),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      keyboardType: TextInputType.number,
      controller: productWeightController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );
    final specialChar = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Product Special Characteristics',icon: Icon(Icons.star),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: specialCharController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    final source = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'source',icon: Icon(Icons.source),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: sourceController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    final destination = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Destination',icon: Icon(Icons.flag),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: destinationController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    final destinationContactMobile = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Destination Mobile',icon: Icon(Icons.phone),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      keyboardType: TextInputType.number,
      controller: destinationContactMobileController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    final destinationContactEmail = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Destination Email',icon: Icon(Icons.email),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: destinationContactEmailController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    final initDate = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Init date',icon: Icon(Icons.calendar_today),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: initDateController,
      onTap: () async{
        DateTime date = DateTime(1900);
        FocusScope.of(context).requestFocus(new FocusNode());

        date = await showDatePicker(
            context: context,
            initialDate:DateTime.now(),
            firstDate:DateTime(1900),
            lastDate: DateTime(2100));

        initDateController.text = formatISOTime(date);},
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
        onPressed: () async {
          //button action
          _saveForm();

          print(" ## Send object - Create Request ## ");

          CreateRequestDTO newReq = new CreateRequestDTO(null,double.parse(productValueController.text), productNameController.text,
              sourceController.text, destinationController.text, destinationContactMobileController.text, destinationContactEmailController.text,
              initDateController.text, null,
              specialCharController.text,double.parse(productWeightController.text),double.parse(productHeightController.text),double.parse(productWidthController.text),
              "WAITING_COLLECTION", null,0.0,null,null);

          var url = Uri.parse('http://ec2-54-209-6-238.compute-1.amazonaws.com:8080/api/requests/create'); //+ userID.toString()
          var body = json.encode(newReq.toJson());
          print(body);

          Map<String,String> headers = {
            'Content-type' : 'application/json',
            'Accept': 'application/json',
            'Authorization' : token,
          };

          var response = await http.post(url, body: body, headers: headers); // send
          print(response.statusCode);

          // parse response in CreateRequest DTO
          var jsonResponse = json.decode(response.body);
          CreateRequestDTO newReqUpdate = new CreateRequestDTO.fromJson(jsonResponse);

          if(response.statusCode == 200 || response.statusCode == 201 || response.statusCode == 204){

            showAlertDialogWithPayment(context, "New Request Created", "Price is "+newReqUpdate.shippingCosts.toString()+". Please choose your payment method:", newReqUpdate, userInfoAux, userID.toString(),token);
            //Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_Main_Area(firstName, userID, token)));
            return response;

          }else{
            showAlertDialog(context, "Error", "Request Creation failed. Please try later");
            return response;
          }
        },
      ),
    );

    return Scaffold(
      appBar: AppBar(
          title: Text('Create your new req . . .'),
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
                    productName,
                    SizedBox(height: 30.0),
                    productValue,
                    SizedBox(height: 30.0),
                    productHeight,
                    SizedBox(height: 30.0),
                    productWidth,
                    SizedBox(height: 30.0),
                    productWeight,
                    SizedBox(height: 30.0),
                    specialChar,
                    SizedBox(height: 30.0),
                    source,
                    SizedBox(height: 30.0),
                    destination,
                    SizedBox(height: 30.0),
                    destinationContactMobile,
                    SizedBox(height: 30.0),
                    destinationContactEmail,
                    SizedBox(height: 30.0),
                    initDate,
                    confirmRequestButton,
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


showAlertDialogWithPayment(BuildContext context, String title, String content, CreateRequestDTO newReqUpdate, UserInfo userInfoAux, String userID, String token) {

  // set up the button
  Widget lebCashButton = TextButton(
    child: Text("LEB Cash"),
    onPressed: () {
      //Navigator.of(context).pop();
      if(userInfoAux.availableBalance >= newReqUpdate.shippingCosts){
          // Possible to pay with LEB cash
        showAlertDialog(context, "Success", "Your wallet was updated.");

      }else{
        // Not possible to pay with LEB cash
        showAlertDialog(context, "Error", "Insufficient money.");
      }
    },
  );

  // set up the button
  Widget creditCardButton = TextButton(
    child: Text("Credit Card"),
    onPressed: () {
      showAlertDialogCreditCard(context, "Simulation page", "you will pay "+newReqUpdate.shippingCosts.toString()+ "with your Credit Card.", userID.toString(),newReqUpdate, token);
    },
  );

  // set up the AlertDialog
  AlertDialog alert = AlertDialog(
    title: Text(title),
    content: Text(content),
    actions: [
      lebCashButton,
      creditCardButton
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

showAlertDialogCreditCard(BuildContext context, String title, String content, String userID, CreateRequestDTO newReqUpdate, String token) {

  // set up the button
  Widget okButton = TextButton(
    child: Text("OK"),
    onPressed: () async {
      // Put to server...
      var url = Uri.parse('http://ec2-54-209-6-238.compute-1.amazonaws.com:8080/api/user-infos/load/'+userID+'/'+newReqUpdate.shippingCosts.toString()); //+userID.toString()

      Map<String,String> headers = {
        'Content-type' : 'application/json',
        'Accept': 'application/json',
        'Authorization' : token,
      };

      final response =  await http.put(url,headers: headers);
      print(response);
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