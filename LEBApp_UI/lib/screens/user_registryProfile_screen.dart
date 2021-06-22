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
import 'package:lebapp_ui/screens/user_registry_screenCredent.dart';
import '../main.dart';

// ignore: camel_case_types
class User_RegistryProfile_Screen extends StatefulWidget {

  @override
  _User_RegistryProfile_ScreenState createState() => _User_RegistryProfile_ScreenState();
}

class _User_RegistryProfile_ScreenState extends State<User_RegistryProfile_Screen> {

  bool valuefirst = false;
  bool valuesecond = false;
  bool valuethird = false;
  bool valuefourth = false;

  @override
    Widget build(BuildContext context) {

    final nextButon = Material(
      child: TextButton(
          child: Text('Finalize Info'),
          style: TextButton.styleFrom(
            alignment: Alignment.bottomCenter,
            primary: Colors.white,
            backgroundColor: Colors.teal,
            onSurface: Colors.grey,
          ),
          onPressed: () {
            Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_Registry_ScreenCredent()));
          }
      ),
    );

    return Scaffold(
      appBar: AppBar(
        title: Text("Your User Profile"),
        backgroundColor: Colors.teal,
      ),
      body: Container(
          padding: new EdgeInsets.all(22.0),
          child: Column(
            children: <Widget>[
              SizedBox(width: 10,),
              Text('Choose one or more profiles:',style: TextStyle(fontSize: 20.0), ),
              CheckboxListTile(
                secondary: const Icon(Icons.add),
                title: const Text('Producer'),
                subtitle: Text('Producer subtext'),
                value: this.valuefirst,
                onChanged: (bool value) {
                  setState(() {
                    this.valuefirst = value;
                    print(valuefirst);
                    extraInfoProducerDialog();
                  });
                },
              ),
              CheckboxListTile(
                controlAffinity: ListTileControlAffinity.trailing,
                secondary: const Icon(Icons.add),
                title: const Text('Transporter'),
                subtitle: Text('Transporter subtext'),
                value: this.valuesecond,
                onChanged: (bool value) {
                  setState(() {
                    this.valuesecond = value;
                    print(valuesecond);
                    extraInfoTransporterDialog();
                  });
                },
              ),
              CheckboxListTile(
                controlAffinity: ListTileControlAffinity.trailing,
                secondary: const Icon(Icons.add),
                title: const Text('Distributor'),
                subtitle: Text('Distributor subtext'),
                value: this.valuethird,
                onChanged: (bool value) {
                  setState(() {
                    this.valuethird = value;
                    print(valuethird);
                  });
                },
              ),
              CheckboxListTile(
                controlAffinity: ListTileControlAffinity.trailing,
                secondary: const Icon(Icons.add),
                title: const Text('Point'),
                subtitle: Text('Point subtext'),
                value: this.valuefourth,
                onChanged: (bool value) {
                  setState(() {
                    this.valuefourth = value;
                    print(valuefourth);
                  });
                },
              ),
              nextButon,
              SizedBox(
                height: 15.0,
              ),
            ],
          )
      ),
    );
  }

  extraInfoProducerDialog(){

    TextEditingController _textFieldController = TextEditingController();

    return showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('Social link'),
            content: TextField(
              controller: _textFieldController,
              textInputAction: TextInputAction.go,
              keyboardType: TextInputType.numberWithOptions(),
              decoration: InputDecoration(hintText: "Enter your social link"),
            ),
            actions: <Widget>[
              new FlatButton(
                child: new Text('Submit'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              )
            ],
          );
        });
  }

  extraInfoTransporterDialog(){

    TextEditingController _textFieldController = TextEditingController();

    return showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('Favourite Transport'),
            content: TextField(
              controller: _textFieldController,
              textInputAction: TextInputAction.go,
              keyboardType: TextInputType.numberWithOptions(),
              decoration: InputDecoration(hintText: "Enter your favourite transport"),
            ),
            actions: <Widget>[
              new FlatButton(
                child: new Text('Submit'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              )
            ],
          );
        });

  }

  extraInfoDistributorDialog(){

  }

  extraInfoPointDialog(){

  }
  /*
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
  }*/
}