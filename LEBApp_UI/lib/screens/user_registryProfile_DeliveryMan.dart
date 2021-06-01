import 'dart:io';

import 'package:flutter/material.dart';
import '../main.dart';

// ignore: camel_case_types
class User_RegistryProfile_DeliveryMan extends StatefulWidget {

  @override
  _User_RegistryProfile_DeliveryManState createState() => _User_RegistryProfile_DeliveryManState();
}

class _User_RegistryProfile_DeliveryManState extends State<User_RegistryProfile_DeliveryMan> {
  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
          title: Text('Delivery Man Info'),
          backgroundColor: Colors.teal),
    );
  }
}