package charlskin.repruja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Audio extends AppCompatActivity {

    private ArrayList<Cancion> listaCanciones;
    private ArrayAdapter<Cancion> adaptador;
    private ListView vistaCanciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        vistaCanciones=(ListView)findViewById(R.id.listaCanciones);
        listaCanciones=new ArrayList<Cancion>();
        getListaCanciones();
        Collections.sort(listaCanciones, new Comparator<Cancion>() {
            public int compare(Cancion a, Cancion b) {
                return a.getTitulo().compareTo(b.getTitulo());
            }
        });
        adaptador=new ArrayAdapter<Cancion>(this,android.R.layout.simple_list_item_1,listaCanciones);

        vistaCanciones.setAdapter(adaptador);
    }
    public void getListaCanciones() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if(musicCursor!=null && musicCursor.moveToFirst()){
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                listaCanciones.add(new Cancion(thisTitle, thisArtist,thisId));
            }
            while (musicCursor.moveToNext());
        }

    }
}
