import 'package:flutter/material.dart';
import 'package:lebapp_ui/models/User.dart';
import 'package:lebapp_ui/screens/user_registry_screenCredent.dart';

// ignore: camel_case_types
class User_RegistryProfile_Screen extends StatefulWidget {

  final User uTest;
  User_RegistryProfile_Screen({Key key, this.uTest}) : super(key: key);

  @override
  _User_RegistryProfile_ScreenState createState() => _User_RegistryProfile_ScreenState();
}

class _User_RegistryProfile_ScreenState extends State<User_RegistryProfile_Screen> {

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
            User uAux = new User(widget.uTest.firstName, widget.uTest.lastName, null,'empty',
                widget.uTest.phoneNumber, widget.uTest.nif,null, widget.uTest.birthday, widget.uTest.address,
                null,null,null);

            print("Objecto antes enviar final: " + uAux.toString());

            Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_Registry_ScreenCredent(uTest: uAux)));

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
              Text(' next ',style: TextStyle(fontSize: 20.0), ),
              nextButon,
              SizedBox(
                height: 15.0,
              ),
            ],
          )
      ),
    );
  }

  /*
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
                  widget.uTest.linkSocial = _textFieldController.text;
                },
              )
            ],
          );
        });
  }



  extraInfoDistributorDialogStartHour(){

    TextEditingController birthdayController = TextEditingController();

    return showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('Working Time - Start'),
            content: TextFormField(
              obscureText: false,
              decoration: InputDecoration(hintText: 'Enter start time',icon: Icon(Icons.calendar_today),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
              controller: birthdayController,
              onTap: () async{
                FocusScope.of(context).requestFocus(new FocusNode());

                final TimeOfDay picked=await showTimePicker(context: context,initialTime: TimeOfDay(hour: 5,minute: 10));

                birthdayController.text = picked.format(context);
                widget.uTest.openingTimeDeliveryMan = birthdayController.text; },

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

*/

}