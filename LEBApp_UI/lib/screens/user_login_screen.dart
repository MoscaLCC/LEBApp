import 'package:flutter/material.dart';
import 'package:lebapp_ui/models/LoginDTO.dart';
import 'package:lebapp_ui/screens/user_main_area.dart';
import 'package:lebapp_ui/screens/user_registry_screen.dart';
import '../main.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class User_Reg_Screen extends StatefulWidget {
  @override
  _User_Reg_ScreenState createState() => _User_Reg_ScreenState();
}

class _User_Reg_ScreenState extends State<User_Reg_Screen> {
  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  // key to form validator
  final _keyForm = GlobalKey<FormState>(); // Our created key

  // Controllers
  final controllerEmailTextField = TextEditingController();
  final controllerPasswordTextField = TextEditingController();

  String selectedProfile = null;

  bool _saveForm(){
    if(_keyForm.currentState.validate()){
      _keyForm.currentState.save();
      return true;
    }else{
      return false;
    }
  }

  @override
  Widget build(BuildContext context) {

    final emailField = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Insert your username.'),
      controller: controllerEmailTextField,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert an email';
        return null;
      },
    );

    final passwordField = TextFormField(
      obscureText: true,
      style: style,
      decoration: InputDecoration(hintText: 'Insert your password.'),
      controller: controllerPasswordTextField,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a password';
        return null;
      },
    );

    final loginButon = Material(
      child: TextButton(
        child: Text('Login'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () async {

          if( _saveForm()){

            print("Link to server to Login ...");

            var  url = Uri.parse('http://192.168.1.110:8080/api/interface/authenticate');
            var body = json.encode({"username": controllerEmailTextField.text, "password": controllerPasswordTextField.text, "rememberMe": "false"});

            Map<String,String> headers = {
              'Content-type' : 'application/json',
              'Accept': 'application/json',
            };

            final response = await http.post(url, body: body, headers: headers);
            var jsonResponse = json.decode(response.body);

            // parse response in login DTO
            LoginDTO loginDTO = new LoginDTO.fromJson(jsonResponse);

            print("Response:");
            print(loginDTO);

            print(loginDTO.token);
            print(loginDTO.userID);
            print(loginDTO.firstName);
            print(loginDTO.lastName);
            print(loginDTO.profiles);

            // select available profile
            await teste(loginDTO.profiles);

            Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) => User_Main_Area(loginDTO.firstName, loginDTO.userID, selectedProfile,loginDTO.token)));

          }else{
            print("validator fail: fill user and pass");
          }
        },
      ),
    );

    final backButon = Material(
      child: TextButton(
        child: Text('BACK BUTTON'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
          //button action
          Navigator.of(context).push(MaterialPageRoute(builder: (context)=>MyApp()));
        },
      ),
    );

    final regButon = Material(
      child: TextButton(
        child: Text('Not signed ? Register now'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
          //button action
          Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_Registry_Screen()));
        },
      ),
    );

    return Scaffold(
      appBar: AppBar(
          title: Text('Login Area'),
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
                    SizedBox(
                      height: 155.0,
                      child: Image.asset(
                        "assets/pp.jpg",
                        fit: BoxFit.contain,
                      ),
                    ),
                    SizedBox(height: 45.0),
                    emailField,
                    SizedBox(height: 25.0),
                    passwordField,
                    SizedBox(
                      height: 35.0,
                    ),
                    loginButon,
                    SizedBox(
                      height: 15.0,
                    ),
                    backButon,
                    SizedBox(
                      height: 15.0,
                    ),
                    regButon,
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

  teste(List<dynamic> profileList){

    return showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('Your Profiles:'),
              content: Container(
                width: double.minPositive,
                child: ListView.builder(
                  shrinkWrap: true,
                  itemCount: profileList.length,
                  itemBuilder: (BuildContext context, int index) {
                    return ListTile(
                      title: Text(profileList[index]),
                      onTap: () {
                        Navigator.pop(context, profileList[index]);
                        if( profileList[index] != null){
                          selectedProfile = profileList[index];
                        }
                      },
                    );
                  },
                ),
              ),
            actions: <Widget>[
              new FlatButton(
                child: new Text('Submit'),
                onPressed: ()  {
                  Navigator.of(context).pop();
                },
              )
            ],
          );
        });
  }
}