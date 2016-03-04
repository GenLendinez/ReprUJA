package charlskin.repruja;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Menu_reproductor_audio extends AppCompatActivity implements View.OnClickListener{

    MediaPlayer reproductor;
    ArrayList<Cancion> listaCancion;
    ImageButton botonplay,botonnext,botonprevious;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_reproductor_audio);
        Intent recolector= getIntent();
        Bundle extra=recolector.getExtras();
        if (extra!=null){
            String tituloC=extra.getString("Titulo");
            //listaCancion=extra.getParcelableArrayList("ListaCanciones");
        }
        reproductor=new MediaPlayer();
        botonplay=(ImageButton) findViewById(R.id.boton_play);
        botonnext=(ImageButton) findViewById(R.id.boton_siguiente);
        botonprevious=(ImageButton) findViewById(R.id.boton_anterior);
        botonplay.setOnClickListener(this);
        botonprevious.setOnClickListener(this);
        botonnext.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.boton_play:

                break;
        }
    }
}
