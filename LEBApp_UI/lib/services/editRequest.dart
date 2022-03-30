import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:http/http.dart' as http;
import 'package:lebapp_ui/models/CreateRequestDTO.dart';
import 'package:lebapp_ui/models/DimensionsDTO.dart';
import 'package:lebapp_ui/models/RidePathDTO.dart';

//General Area User Main Page
// ignore: camel_case_types
class EditRequest extends StatefulWidget {

  String firstName;
  int userID;
  String token;
  CreateRequestDTO requestSelected;
  int reqID;

  EditRequest(this.firstName, this.userID,this.token,this.requestSelected,this.reqID);

  @override
  _EditRequestState createState() => _EditRequestState(firstName,userID,token,requestSelected,reqID);
}

class _EditRequestState extends State<EditRequest> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  String firstName;
  int userID;
  String token;
  CreateRequestDTO requestSelected;
  int reqID;

  _EditRequestState(this.firstName,this.userID,this.token,this.requestSelected,this.reqID);

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

    print(reqID.toString());

    productNameController.text = requestSelected.productName;
    final productName = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(icon: Icon(Icons.people),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: productNameController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a name';
        return null;
      },
    );

    productValueController.text = requestSelected.productValue.toString();
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

    productHeightController.text = requestSelected.hight.toString();
    final productHeight = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Height',icon: Icon(Icons.phone),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      keyboardType: TextInputType.number,
      controller: productHeightController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    productWidthController.text = requestSelected.width.toString();
    final productWidth = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Width',icon: Icon(Icons.phone),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      keyboardType: TextInputType.number,
      controller: productWidthController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    productWeightController.text = requestSelected.weight.toString();
    final productWeight = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Weight',icon: Icon(Icons.phone),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      keyboardType: TextInputType.number,
      controller: productWeightController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    specialCharController.text = requestSelected.specialCharacteristics;
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

    sourceController.text = requestSelected.source;
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

    destinationController.text = requestSelected.destination;
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

    destinationContactMobileController.text = requestSelected.destinationContactMobile;
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

    destinationContactEmailController.text = requestSelected.destinationContactEmail;
    final destinationContactEmail = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Destination Email',icon: Icon(Icons.phone),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: destinationContactEmailController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a email';
        return null;
      },
    );

    initDateController.text = requestSelected.initDate.toString();
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


    // ----- buttons ------
    final confirmEditRequestButton = Material(
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

          print(" ## Send object - Edit Request ## ");

          CreateRequestDTO newReq = new CreateRequestDTO(requestSelected.id,double.parse(productValueController.text), productNameController.text,
              sourceController.text, destinationController.text, destinationContactMobileController.text, destinationContactEmailController.text,
              initDateController.text, requestSelected.expirationDate,
              specialCharController.text,double.parse(productWeightController.text),double.parse(productHeightController.text),double.parse(productWidthController.text),
              requestSelected.status,requestSelected.shippingCosts,requestSelected.rating,requestSelected.ownerRequest,requestSelected.transporter);

          var url = Uri.parse('http://ec2-54-209-6-238.compute-1.amazonaws.com:8080/api/requests/'+reqID.toString());
          var body = json.encode(newReq.toJson());
          print(body);

          Map<String,String> headers = {
            'Content-type' : 'application/json',
            'Accept': 'application/json',
            'Authorization' : token,
          };

          final response = await http.put(url, body: body, headers: headers);
          print(response);

          if(response.statusCode == 200){
            showAlertDialog(context, "Edit Request", "Success");
            return response;

          }else{
            showAlertDialog(context, "Edit Request", "Error");
            return response;
          }
        },
      ),
    );


    return Scaffold(
      appBar: AppBar(
          title: Text('Edit your request'),
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
                    confirmEditRequestButton,
                    SizedBox(height: 15.0,),
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

showAlertDialog(BuildContext context, String title, String content) {

  // set up the button
  Widget okButton = TextButton(
    child: Text("OK"),
    onPressed: () { },
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