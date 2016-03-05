package charlskin.repruja;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class Menu_reproductor_audio extends AppCompatActivity implements View.OnClickListener{

    MediaPlayer reproductor;
    ArrayList<Cancion> listaCancion;
    ImageButton botonplay,botonnext,botonprevious;
    TextView campoTitulo,campoArtista;
    String tituloActual,artistaActual,rutaActual;
    int cancionActual;
    boolean reproduciendo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_reproductor_audio);
        Intent recolector= getIntent();
        Bundle extra=recolector.getExtras();
        cancionActual=0;
        artistaActual=rutaActual="null";
        reproduciendo=false;
        if (extra!=null) {
            tituloActual = extra.getString("Titulo");
            listaCancion = extra.getParcelableArrayList("ListaCanciones");
        }
        reproductor=new MediaPlayer();
        botonplay=(ImageButton) findViewById(R.id.boton_play);
        botonnext=(ImageButton) findViewById(R.id.boton_siguiente);
        botonprevious=(ImageButton) findViewById(R.id.boton_anterior);
        campoTitulo=(TextView) findViewById(R.id.campo_titulo);
        campoArtista=(TextView)findViewById(R.id.campo_artista);
        try {
            iniciarReproductor();
        } catch (IOException e) {
            e.printStackTrace();
        }
        botonplay.setOnClickListener(this);
        botonprevious.setOnClickListener(this);
        botonnext.setOnClickListener(this);
    }

    void iniciarReproductor() throws IOException {
        for (int i=0;i<listaCancion.size();i++){
            if (tituloActual==listaCancion.get(i).getTitulo()){
                artistaActual=listaCancion.get(i).getArtista();
                rutaActual=listaCancion.get(i).getRuta();
                cancionActual=i;
            }
        }
        try {
            campoArtista.setText(artistaActual);
            campoTitulo.setText(tituloActual);
            reproductor.setDataSource(rutaActual);
            reproductor.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.boton_play:
                if (!reproduciendo ) {
                    //botonplay.setBackground(getDrawable(R.drawable.pauseb)); Aqui hay un problema con la version de la API ya lo solucionaremos
                    reproductor.start();
                    reproduciendo=true;
                }else{
                    reproductor.pause();
                    reproduciendo=false;
                }
                break;
        }
    }
}
