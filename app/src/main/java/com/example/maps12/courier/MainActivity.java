package com.example.maps12.courier;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    static final int SEE_ON_MAP = 1;
    int id1;
    Intent intent;
    String FirstName;
    TextView textView;
    int mId;

    DBFunction db = new DBFunction(this);

    ListView LView;
    SimpleCursorAdapter sAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db.open();

        String[] from = new String[] {db.ORDER_NUMBER.toString(), db.PRODUCT_NAME, db.NAME,  db.PHONE_NUMBER.toString()};

        int[] to = new int[] { R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4};

      sAdapter = new SimpleCursorAdapter(this, R.layout.list_item, null, from, to, 0);
       LView = (ListView) findViewById(R.id.main_list);

        LView.setAdapter(sAdapter);

       getSupportLoaderManager().initLoader(0,null,this);


        //menu for checking items
        LView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                Cursor cursor = (Cursor) sAdapter.getItem(position);
                mId = cursor.getInt(cursor.getColumnIndex("_id"));


                PopupMenu popupmenu = new PopupMenu(MainActivity.this, view);
                Menu menu = popupmenu.getMenu();
                menu.add(0, 1, 0, "Показати на карті");
                menu.add(0, 2, 1,"Відмітити як доставлене");


                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case 1:
                                intent = new Intent();
                                intent.putExtra("ID_db", mId);
                                intent.setClass(MainActivity.this, MapsActivity.class);
                                startActivity(intent);
                                break;

                            case 2:

                        }
                        return true;


                        }

                });

                popupmenu.show();
                return true;
            }

        });




    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == SEE_ON_MAP) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            //db.delRec(acmi.id);
            // получаем новый курсор с данными
            getSupportLoaderManager().getLoader(0).forceLoad();
            return true;
        }
        return super.onContextItemSelected(item);
    }


    public void OnClick (View v){
        db.open();
        switch (v.getId()){
           /* case R.id.but_add:
               ContentValues cv = new ContentValues();

                //cv.put(db.COLUMN_NUMBER,121);
                cv.put(db.NAME,"Inew");
                db.mDB.insert("List", null,cv);
                db.close();
                break;


             case R.id.button_read:
                Cursor cursor = db.mDB.query("List",null,null,null,null,null,null);

                 if(cursor.moveToFirst()){

                     int idColIndex = cursor.getColumnIndex("_id");
                     //int numbColIndex = cursor.getColumnIndex("Number");
                     int FirstNameInd = cursor.getColumnIndex("FirstName");

                 do {
                    id1 = cursor.getInt(idColIndex);
                     //Number = cursor.getInt(numbColIndex);
                     FirstName = cursor.getString(FirstNameInd);
                 }
                 while (cursor.moveToNext());
                 }
                 else cursor.close();
                textView = (TextView) findViewById(R.id.textView_x);
                 registerForContextMenu(textView);
                 textView.setText(id1 +" "+ FirstName +" " + "hjh");
                 break;
*/
            case R.id.button:

                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);

                break;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        //закрываем подключение при выходе
        db.close();
    }

@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return  new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        sAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {

        DBFunction db;

        public MyCursorLoader(Context context, DBFunction db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            //Cursor cursor = db.getDeliver();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }

    }
}
