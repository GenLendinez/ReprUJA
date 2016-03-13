package charlskin.repruja;



import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Menu_reproductor_audio extends AppCompatActivity implements View.OnClickListener{

    private MediaPlayer reproductor;
    private ArrayList<Cancion> listaCancion;
    private ImageButton botonplay,botonnext,botonprevious;
    private TextView campoTitulo,campoArtista;
    private SeekBar barra_duracion;
    private Timer mei= new Timer();
    private String tituloActual,artistaActual,rutaActual;
    private ScheduledExecutorService gestorHilos;
    private int cancionActual;
    private boolean reproduciendo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_reproductor_audio);
        Bundle extra=getIntent().getExtras();
        cancionActual=-1;
        artistaActual=rutaActual="null";
        reproduciendo=true;
        if (extra!=null) {
            tituloActual = extra.getString("Titulo");
            listaCancion = extra.getParcelableArrayList("listaCanciones");
        }
        reproductor=new MediaPlayer();
        botonplay=(ImageButton) findViewById(R.id.boton_play);
        gestorHilos= Executors.newScheduledThreadPool(1);
        botonnext=(ImageButton) findViewById(R.id.boton_siguiente);
        barra_duracion=(SeekBar) findViewById(R.id.dura_cancion);
        botonprevious=(ImageButton) findViewById(R.id.boton_anterior);
        campoTitulo=(TextView) findViewById(R.id.campo_titulo);
        campoArtista=(TextView)findViewById(R.id.campo_artista);
        try {
            iniciarReproductor();
        } catch (IOException e) {
            e.printStackTrace();
        }
        barra_duracion.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    seekBar.setProgress(progress);
                    reproductor.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        botonplay.setOnClickListener(this);
        botonprevious.setOnClickListener(this);
        botonnext.setOnClickListener(this);
    }

    void iniciarReproductor() throws IOException {
        for (int i=0;i<listaCancion.size();i++){
            if (listaCancion.get(i).getTitulo().equals(tituloActual)){
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
            barra_duracion.setMax(reproductor.getDuration());
            reproductor.start();
            gestorHilos.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    barra_duracion.setProgress(reproductor.getCurrentPosition());
                }
            },1,1, TimeUnit.MILLISECONDS);
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
    void reproducir() throws IOException {
        campoArtista.setText(listaCancion.get(cancionActual).getArtista());
        campoTitulo.setText(listaCancion.get(cancionActual).getTitulo());
        reproductor.stop();
        reproductor=new MediaPlayer();
        reproductor.setDataSource(listaCancion.get(cancionActual).getRuta());
        reproductor.prepare();
        barra_duracion.setMax(reproductor.getDuration());
        reproductor.start();
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.boton_play:
                if (!reproduciendo ) {
                    //botonplay.setBackground(getDrawable(R.drawable.pauseb)); //Aqui hay un problema con la version de la API ya lo solucionaremos
                    reproductor.start();
                    reproduciendo=true;
                }else{
                    reproductor.pause();
                    reproduciendo=false;
                }
                break;
            case R.id.boton_siguiente:
                cancionActual++;
                try {
                    reproducir();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.boton_anterior:
                cancionActual--;
                try {
                    reproducir();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.dura_cancion:
                System.out.println("pene");
                break;
        }
    }
}
