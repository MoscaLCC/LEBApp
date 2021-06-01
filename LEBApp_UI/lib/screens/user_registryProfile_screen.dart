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
import '../main.dart';

// ignore: camel_case_types
class User_RegistryProfile_Screen extends StatefulWidget {

  @override
  _User_RegistryProfile_ScreenState createState() => _User_RegistryProfile_ScreenState();
}

class _User_RegistryProfile_ScreenState extends State<User_RegistryProfile_Screen> {

  String nomeCidade="";
  var _cidades =['Producer','Transporter','Delivery Man','Point'];
  var _itemSelecionado = 'Producer';

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text("Your User Profile"),
        backgroundColor: Colors.teal,
      ),
      body: criaDropDownButton(),
    );
  }
  criaDropDownButton() {

    final nextButon = Material(
      child: TextButton(
          child: Text('Select'),
          style: TextButton.styleFrom(
            alignment: Alignment.bottomCenter,
            primary: Colors.white,
            backgroundColor: Colors.teal,
            onSurface: Colors.grey,
          ),
          onPressed: () {
            debugPrint('teste button -->> $_itemSelecionado');

            if(_itemSelecionado == "Transporter"){
              Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_RegistryProfile_Transporter()));
            }else if(_itemSelecionado == "Delivery Man"){
              Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_RegistryProfile_DeliveryMan()));
            }else if(_itemSelecionado == "Point"){
              Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_RegistryProfile_Point()));
            }else if(_itemSelecionado == "Producer"){
              Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_RegistryProfile_Producer()));
            }
          }
      ),
    );

    return Container(
      child: Column(
        children: <Widget>[
          Text("Select your user profile"),
          TextField(
            onSubmitted: (String userInput) {
              setState(() {
                debugPrint('chamei setState');
                nomeCidade = userInput;
              });
            },
          ),
          DropdownButton<String>(
              icon: const Icon(Icons.arrow_downward),
              iconSize: 24,
              elevation: 16,
              style: const TextStyle(
                  color: Colors.teal
              ),
              underline: Container(
                height: 2,
                color: Colors.teal,
              ),
              items : _cidades.map((String dropDownStringItem) {
                return DropdownMenuItem<String>(
                  value: dropDownStringItem,
                  child: Text(dropDownStringItem, style: TextStyle(fontSize: 15.0)),
                );
              }).toList(),
              onChanged: ( String novoItemSelecionado) {
                _dropDownItemSelected(novoItemSelecionado);
                setState(() {
                  this._itemSelecionado =  novoItemSelecionado;
                });
              },
              value: _itemSelecionado
          ),
          Text("\nSelected Profile: $_itemSelecionado \n",
            style: TextStyle(fontSize: 20.0),
          ),
          nextButon,
          SizedBox(
            height: 15.0,
          ),
        ],
      ),
    );
  }
  void _dropDownItemSelected(String novoItem){
    setState(() {
      this._itemSelecionado = novoItem;

    });
  }
}