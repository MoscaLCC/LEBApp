import 'package:flutter/material.dart';

void main() {
  runApp(
      MaterialApp(
        home: Scaffold(
          body: UserForm(),
        ),
      )
  );
}

class UserForm extends StatefulWidget {

  @override
  _UserFormState createState() => _UserFormState();
}

class User {
  String name;
  String password;
  String email;
  User({ this.name, this.password, this.email });
}

class _UserFormState extends State<UserForm> {

  final _keyForm = GlobalKey<FormState>(); // Our created key
  final _name = TextEditingController();
  final _password = TextEditingController();
  final _email = TextEditingController();

  void _saveForm(){

    if(_keyForm.currentState.validate()){
      _keyForm.currentState.save();
      print(_name);
      print(_email);
    }
  }

  @override
  Widget build(BuildContext context) {

    // The form widget
    return Form(
      key: _keyForm,
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: ListView(
          children: [
            Container(
              padding: const EdgeInsets.symmetric(vertical: 20),
              child: Text('Welcome to LEB - User Registry'),
            ),
            TextFormField(
              controller: _name,
              decoration: InputDecoration(hintText: 'Insert your name.'),
              validator: (value) {
                if (value.isEmpty) return 'You have to insert a name';

                return null;
              },
              onSaved: (newValue) => User().name = newValue,
            ),
            TextFormField(
              controller: _password,
              obscureText: true,
              decoration: InputDecoration(hintText: 'The password to log in.'),
              validator: (value) {
                if (value.length < 7)
                  return 'Password must have at least 6 chars.';

                return null;
              },
              onSaved: (newValue) => User().password = newValue,
            ),
            TextFormField(
              controller: _email,
              decoration:
              InputDecoration(hintText: 'E-mail to use for log in.'),
              validator: (value) {
                if (!value.contains('@gmail.com'))
                  return 'Only gmail emails allowed.';

                return null;
              },
              onSaved: (newValue) => User().email = newValue,
            ),
            RaisedButton(
              child: Text('Apply'),
              onPressed: () {
                _saveForm();
              },
            )
          ],
        ),
      ),
    );
  }
}
