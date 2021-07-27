import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
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

  // key to form validator
  final _keyForm = GlobalKey<FormState>(); // Our created key

  // Controllers
  final productNameController = TextEditingController();
  final productValueController = TextEditingController();
  final descriptionController = TextEditingController();
  final specialCharController = TextEditingController();
  final sourceController = TextEditingController();
  final destinationController = TextEditingController();
  final destinationContactController = TextEditingController();
  final initDateController = TextEditingController();
  final expirationDateController = TextEditingController();
  final estimatedDateController = TextEditingController();
  final deliveryTimeController = TextEditingController();
  final shippingCostsController = TextEditingController();
  // Controllers (DImensionsDTO)
  final productHeightController = TextEditingController();
  final productWidthController = TextEditingController();
  final productDepthController = TextEditingController();
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

    final productDepth = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Depth',icon: Icon(Icons.phone),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      keyboardType: TextInputType.number,
      controller: productDepthController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

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

    final destinationContact = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Destination Contact',icon: Icon(Icons.phone),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      keyboardType: TextInputType.number,
      controller: destinationContactController,
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

        initDateController.text = DateFormat("yyyy-MM-dd").format(date);},
    );

    final expirationDate = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Expiration date',icon: Icon(Icons.calendar_today),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: expirationDateController,
      onTap: () async{
        DateTime date = DateTime(1900);
        FocusScope.of(context).requestFocus(new FocusNode());

        date = await showDatePicker(
            context: context,
            initialDate:DateTime.now(),
            firstDate:DateTime(1900),
            lastDate: DateTime(2100));

        expirationDateController.text = DateFormat("yyyy-MM-dd").format(date);},
    );

    final estimatedDate = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Estimated date',icon: Icon(Icons.calendar_today),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: estimatedDateController,
      onTap: () async{
        DateTime date = DateTime(1900);
        FocusScope.of(context).requestFocus(new FocusNode());

        date = await showDatePicker(
            context: context,
            initialDate:DateTime.now(),
            firstDate:DateTime(1900),
            lastDate: DateTime(2100));

        estimatedDateController.text = DateFormat("yyyy-MM-dd").format(date);},
    );

    final deliveryTime = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Delivery time',icon: Icon(Icons.phone),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      keyboardType: TextInputType.number,
      controller: deliveryTimeController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      },
    );

    final shippingCosts = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Shipping costs',icon: Icon(Icons.phone),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      keyboardType: TextInputType.number,
      controller: shippingCostsController,
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
          _saveForm();

          print(" ## Send object - Create Request ## ");

          DimensionsDTO dim = new DimensionsDTO(500.0, 400.0, 80.0,20.0);
          //DimensionsDTO dim = new DimensionsDTO(double.parse(productHeightController.text), double.parse(productWidthController.text), double.parse(productDepthController.text),double.parse(productWeightController.text));

          RidePathDTO ride = new RidePathDTO("Guimarães", "Braga", "16", "20");

          //CreateRequestDTO newReq = new CreateRequestDTO(29.0, "Caixa Bolos", "Guimarães", "Braga", "916585452", DateTime.now().toString(), DateTime.now().toString(), "Bolos secos", "tem bolor", "OPENED", DateTime.now().toString(), "20", 0.75, 0.0, dim, ride, null);
          CreateRequestDTO newReq = new CreateRequestDTO(null,double.parse(productValueController.text), productNameController.text, sourceController.text,
             destinationController.text, destinationContactController.text, DateTime.parse(initDateController.text),
              DateTime.parse(expirationDateController.text), descriptionController.text, specialCharController.text, "OPENED", DateTime.parse(estimatedDateController.text),
              deliveryTimeController.text, double.parse(shippingCostsController.text), 0.0, dim, ride, null);


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
          title: Text('Create your new req'),
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
                    description,
                    SizedBox(height: 30.0),
                    productHeight,
                    SizedBox(height: 30.0),
                    productWidth,
                    SizedBox(height: 30.0),
                    productDepth,
                    SizedBox(height: 30.0),
                    productWeight,
                    SizedBox(height: 30.0),
                    specialChar,
                    SizedBox(height: 30.0),
                    source,
                    SizedBox(height: 30.0),
                    destination,
                    SizedBox(height: 30.0),
                    destinationContact,
                    SizedBox(height: 30.0),
                    initDate,
                    SizedBox(height: 30.0),
                    expirationDate,
                    SizedBox(height: 30.0),
                    estimatedDate,
                    SizedBox(height: 30.0),
                    deliveryTime,
                    SizedBox(height: 30.0),
                    shippingCosts,
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


