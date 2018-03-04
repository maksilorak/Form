package com.nelsonbenitez.formulario;



import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    EditText fecha, LOGIN, PASSWORD,CONFIRMA_PSWD, EMAIL; // fecha que se vera en la aplicacion, es un editText
    Calendar calendario; //se
    DatePickerDialog seleccion; //para almacenar la fecha seleccionada
    int dia, mes, anno; // para almacenar el dia, mes y añio de la fecha
    Spinner sp_Ciudades;  //spinner para las ciudades
    String Ciudad, sexo="";  // para almacenar la ciudad de nacimiento
    ArrayList<String> hobbies = new ArrayList<String>(); //Array para los joves , donde se almacenan como strings
    TextView respuestas_finales;  //Para almacenar todas las respuestas de los checkbox


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fecha= (EditText) findViewById(R.id.fechaNacimiento); //SE BUSCA EL EDITTEXT DE LA FECHA
        LOGIN=(EditText) findViewById(R.id.login);
        PASSWORD= (EditText) findViewById(R.id.password);
        CONFIRMA_PSWD= (EditText) findViewById(R.id.confirma_password);
        EMAIL= (EditText) findViewById(R.id.email);
        respuestas_finales= (TextView) findViewById(R.id.datos_registro);
        respuestas_finales.setEnabled(false);
        sp_Ciudades= (Spinner) findViewById(R.id.ciudades); //SE BUSCA EL SPINER DE LAS CIUDADES
        sp_Ciudades.setOnItemSelectedListener(this); //listener para las ciudad cuando se realiza la selección


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ciudades, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sp_Ciudades.setAdapter(adapter);

    }


    public void getFecha(View view) {
        calendario=Calendar.getInstance();
        dia= calendario.get(Calendar.DAY_OF_MONTH);
        mes= calendario.get(Calendar.MONTH);
        anno= calendario.get(Calendar.YEAR);
        seleccion= new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                fecha.setText(mDay+"/"+(mMonth+1)+"/"+mYear);
            }
        },dia,mes,anno);

        seleccion.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //se obtiene el valor de la ciudad seleccionada
        Ciudad= parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_guitarra:
                if (checked){
                    hobbies.add("Guitarra");}

                else{
                    hobbies.remove("Guitarra");
                }
                break;
            case R.id.checkbox_musica:
                if (checked){
                    hobbies.add("Música");}

                else{
                    hobbies.remove("Música");
                }
                break;

            case R.id.checkbox_idiomas:
                if (checked){
                    hobbies.add("Idiomas");}

                else{
                    hobbies.remove("Idiomas");
                }
                break;


            case R.id.checkbox_viajes:
                if (checked){
                    hobbies.add("Viajes");}

                else{
                    hobbies.remove("Viajes");
                }
                break;
            // TODO: Veggie sandwich
        }

    }

    public void seleccionFinal(View view) {

        String date=fecha.getText().toString();
        String login=LOGIN.getText().toString();
        String pswd= PASSWORD.getText().toString();
        String confirma_pswd= CONFIRMA_PSWD.getText().toString();
        String mail= EMAIL.getText().toString();
        String Otros_datos="";
        String selecccion_final_hobbies="Hobbies\n\n";

        if (login.length()!=0 && pswd.length()!=0 && confirma_pswd.length()!=0 && mail.length()!=0 && date.length()!=0 && sexo.length()!=0
                && date.length()!=0 && Ciudad.length()!=0 && hobbies.size()!=0)
        {

            if (pswd.equals(confirma_pswd) && EmailEsValido(mail) && PasswordEsValido(pswd) && UsuarioValido(login) )
            {
                Otros_datos="Login: "+ login+ "\n"
                        + "Password: "+ pswd + "\n"
                        + "Email: "+ mail + "\n"
                        +"Sexo: " + sexo + "\n"
                        +"Ciudad de Nacimiento: " + Ciudad + "\n"
                        + "Fecha de Nacimiento: " + date + "\n";

                for (String Selections : hobbies )
                {
                    selecccion_final_hobbies=selecccion_final_hobbies+Selections+"\n";
                }
            }

            else{
                //si el password es válido pero no coinciden las contraseñas
                if (PasswordEsValido(pswd) && !pswd.equals(confirma_pswd)){
                    selecccion_final_hobbies="Las contraseñas no coinciden";
                    Otros_datos="";
                }
                //Password no es válido, no tiene 9 caracteres mínimo
                else if (!PasswordEsValido(pswd)){
                    selecccion_final_hobbies="Password no es válido. Debe ser de mínimo 9 caracteres";
                    Otros_datos="";
                }

                else if (!EmailEsValido(mail)){
                    selecccion_final_hobbies="Email no es válido";
                    Otros_datos="";
                }

                else if (!UsuarioValido(login)){
                    selecccion_final_hobbies="Login no es válido.  Debe ser de mínimo 5 caracteres";
                    Otros_datos="";
                }
            }

            selecccion_final_hobbies=Otros_datos+selecccion_final_hobbies+":\n";
        }

        else {

            selecccion_final_hobbies="Favor Validar el formulario.  Faltan datos por llenar";
        }



        respuestas_finales.setText(selecccion_final_hobbies);
        respuestas_finales.setEnabled(true);
    }

    private boolean UsuarioValido(String login) {
        if (login!=null && login.length()>=5){return true;}
        else{return false;}
    }


    //sE VALIDA QUE SEA UN PASSWORD DE 9 O MAS CARACTERES
    private boolean PasswordEsValido(String pswd) {

        if (pswd!=null && pswd.length()>=9){return true;}
        else{return false;}
    }

    //VALIDA QUE EL EMAIL SEA UN EMAIL VALIDO
    private boolean EmailEsValido(String mail) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = mail;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.masculino:
                if (checked)
                    sexo="Masculino";
                break;
            case R.id.femenino:
                if (checked)
                    sexo="Femenino";
                break;
        }

    }
}

