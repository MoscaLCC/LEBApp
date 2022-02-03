import 'package:flutter/material.dart';
import 'package:lebapp_ui/models/User.dart';
import 'package:lebapp_ui/models/UserInfo.dart';

class User_Edit_Info extends StatefulWidget {

  String firstName;
  int userID;
  String token;
  UserInfo userDTO;

  User_Edit_Info(this.firstName, this.userID,this.token,this.userDTO);

  @override
  _User_Edit_InfoState createState() => _User_Edit_InfoState(firstName,userID,token, userDTO);
}

class _User_Edit_InfoState extends State<User_Edit_Info> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  String firstName;
  int userID;
  String token;
  UserInfo userDTO;

  _User_Edit_InfoState(this.firstName,this.userID,this.token, this.userDTO);

  // key to form validator
  final _keyForm = GlobalKey<FormState>(); // Our created key

  // Controllers
  final addressController = TextEditingController();

  void _saveForm(){
    if(_keyForm.currentState.validate()){
      _keyForm.currentState.save();
    }
  }

  @override
  Widget build(BuildContext context) {

    addressController.text = userDTO.address;
    final addressField = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(icon: Icon(Icons.add_road),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: addressController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a address';
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
        onPressed: () async {
          //button action
          _saveForm();

          print(" ## Send object - Edit user Info ## ");

        },
      ),
    );

    //--------------------------------------------------------------------------
    // -------------------------------------------------------------------------

    return Scaffold(
      appBar: AppBar(
          title: Text('Create your new request'),
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
                    addressField,
                    SizedBox(
                      height: 30.0,
                    ),
                    confirmRequestButton,

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