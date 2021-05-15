import 'package:flutter/material.dart';

import '../main.dart';

class User_Reg_Screen extends StatefulWidget {
  @override
  _User_Reg_ScreenState createState() => _User_Reg_ScreenState();
}
class _User_Reg_ScreenState extends State<User_Reg_Screen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
          title: const Text('New user Registry Screen'),
          backgroundColor: Colors.teal),
      body: Center(
        child: TextButton(
          child: Text('BACK'),
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
        )
      ),
    );
  }
}

