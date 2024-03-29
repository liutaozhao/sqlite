package com.example.database;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SQLiteDatabase db;
    private MainActivity mContext;
    private int i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = MainActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDBOpenHelper myDBHelper = new MyDBOpenHelper(mContext, "my.db", null, 1);
        db = myDBHelper.getWritableDatabase();
        bindViews();
    }

    private void bindViews() {
        Button btn_insert = (Button) findViewById(R.id.btn_insert);
        Button btn_query = (Button) findViewById(R.id.btn_query);
        Button btn_update = (Button) findViewById(R.id.btn_update);
        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        if(btn_query != null)
            btn_query.setOnClickListener(this);
        if(btn_insert != null)
            btn_insert.setOnClickListener(this);
        if(btn_update != null)
            btn_update.setOnClickListener(this);
        if(btn_delete != null)
            btn_delete.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                ContentValues values1 = new ContentValues();
                //name为数据库中属性名,第二个为欲插入的值
                values1.put("name", "呵呵~" + i);
                i++;
                //参数依次是：表名，强行插入null值得数据列的列名，一行记录的数据
                db.insert("person", null, values1);
                Toast.makeText(mContext, "插入完毕~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query:
                StringBuilder sb = new StringBuilder();
                //参数依次是:表名，列名，where约束条件，where中占位符提供具体的值，指定group by的列，进一步约束
                //指定查询结果的排序方式
                Cursor cursor = db.query("person", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex("_id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        sb.append("id：" + id + "：" + name + "\n");
                    } while (cursor.moveToNext());
                }
                cursor.close();
                Toast.makeText(mContext, sb.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update:
                ContentValues values2 = new ContentValues();
                values2.put("name", "嘻嘻~");
                //参数依次是表名，修改后的值，where条件，以及约束，如果不指定三四两个参数，会更改所有行
                db.update("person", values2, "name = ?", new String[]{"呵呵~2"});
                break;
            case R.id.btn_delete:
                //参数依次是表名,where条件,约束
                db.delete("person", "_id = ?", new String[]{"3"});
                //多个条件
//                db.delete("person", "_id=? and name=?", new String[]{"1", "小名"});
                break;
        }
    }
}
