package charlskin.repruja;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Menu_reproductor_audio extends AppCompatActivity implements View.OnClickListener{

    private MediaPlayer reproductor;
    private ArrayList<Cancion> listaCancion;
    private Handler mago=new Handler();
    private ImageButton botonplay,botonnext,botonprevious;
    private TextView campoTitulo,campoArtista,duracionAct,duracionTo;
    private ImageView imageSong;
    private SeekBar barra_duracion;
    private Timer mei= new Timer();
    private String tituloActual,artistaActual,rutaActual;
    private ScheduledExecutorService gestorHilos;
    private int cancionActual,minutos,segundos,minutosTo,segundosTo;
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
        botonplay.setImageResource(R.drawable.pauseb);
        botonplay.setBackground(null);
        gestorHilos= Executors.newScheduledThreadPool(1);
        botonnext=(ImageButton) findViewById(R.id.boton_siguiente);
        imageSong=(ImageView) findViewById(R.id.image_Song);
        barra_duracion=(SeekBar) findViewById(R.id.dura_cancion);
        duracionAct=(TextView) findViewById(R.id.duracionActualizada);
        duracionTo=(TextView)findViewById(R.id.duracionTotal);
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
            if (!listaCancion.get(cancionActual).getRutaImagen().equals("null")){
                System.out.println(listaCancion.get(cancionActual).getRutaImagen());
                File imgFile = new File(listaCancion.get(cancionActual).getRutaImagen());
                if (imgFile.exists()) {
                    Bitmap bitImage = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageSong.setImageBitmap(bitImage);
                }
            }else{
                imageSong.setImageResource(R.drawable.nosong);
            }
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
                    mago.post(new Runnable() {
                        @Override
                        public void run() {
                            segundos=reproductor.getCurrentPosition()/1000;
                            minutos = segundos /60;
                            segundos= segundos % 60;
                            duracionAct.setText(minutos+":"+segundos);
                            segundosTo=reproductor.getDuration()/1000;
                            minutosTo=segundosTo/60;
                            segundosTo=segundosTo%60;
                            segundosTo=(segundosTo-segundos);
                            segundosTo=segundosTo%60;
                            minutosTo=minutosTo-minutos;
                            duracionTo.setText((minutosTo)+":"+segundosTo);
                            if (minutosTo==0 && segundosTo==0){
                                cancionActual++;
                                cancionActual=cancionActual%listaCancion.size();
                                try {
                                    reproducir();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
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
        botonplay.setImageResource(R.drawable.pauseb);
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
                if (!reproduciendo) {
                    botonplay.setImageResource(R.drawable.pauseb);
                    reproductor.start();
                    reproduciendo=true;
                }else{
                    botonplay.setImageResource(R.drawable.play_64);
                    reproductor.pause();
                    reproduciendo=false;
                }
                break;
            case R.id.boton_siguiente:
                if (cancionActual>=listaCancion.size()-1){
                    cancionActual=-1;
                }
                cancionActual++;
                try {
                    reproducir();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.boton_anterior:
                if (cancionActual<=0){
                    cancionActual=listaCancion.size();
                }
                cancionActual--;
                try {
                    reproducir();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
