package upm.pmd.grupo14;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import upm.pmd.grupo14.common.Category;
import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.tasks.DownloadArticleEditTask;
import upm.pmd.grupo14.tasks.DownloadOneArticleTask;
import upm.pmd.grupo14.tasks.UploadArticleTask;
import upm.pmd.grupo14.util.ImageSerializer;
import upm.pmd.grupo14.util.WebServices;

public class ArticleEditActivity extends AppCompatActivity {

    private static final int CODE_OPEN_IMAGE = 10;
    private EditText[] et;

    public Bitmap bitmap;
    public String media_type;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_edit);

        et = new EditText[]{(EditText)findViewById(R.id.txt_edit_title),
                (EditText)findViewById(R.id.txt_edit_subtitle),
                (EditText)findViewById(R.id.txt_edit_abstract),
                (EditText)findViewById(R.id.txt_edit_body)};

        Intent intent = getIntent();
        if(!intent.getStringExtra(Constants.ID_ARTICLE).equals("")){
            DownloadArticleEditTask doat = new DownloadArticleEditTask(this);
            doat.execute(new String[]{intent.getStringExtra(Constants.ID_ARTICLE)});
        }

        Spinner spCategory=findViewById(R.id.spn_categories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        Button btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticleEditActivity.this.finish();
            }
        });

        Button btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> lista = new ArrayList<String>();
                boolean correct = true;
                for (int i=0; i<et.length ;i++ ){
                    String text = et[i].getText().toString();
                    if(text.trim().equals("")){
                        correct=false;
                        et[i].setBackgroundColor(getResources().getColor(R.color.clr_wrong));
                    }
                    else{
                        lista.add(text);
                    }
                }
                if(correct){
                    lista.add(Category.values()[spCategory.getSelectedItemPosition()].name());
                    if(bitmap!=null && media_type!=null){
                        lista.add(ImageSerializer.imgToBase64String(bitmap));
                        lista.add(media_type);
                    }

                    UploadArticleTask uat = new UploadArticleTask(ArticleEditActivity.this,intent.getStringExtra(Constants.ID_ARTICLE));
                    String [] prueba = new String [lista.size()];
                    for (int i = 0; i < lista.size(); i++){
                        prueba[i] = lista.get(i);
                    }
                    uat.execute(prueba);
                }
            }
        });

        Button btn_img_sel = findViewById(R.id.btn_img_sel);
        btn_img_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(i,CODE_OPEN_IMAGE);
            }
        });

        for(int i=0; i<et.length;i++){
            EditText editText = et[i];
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    editText.setBackgroundColor(getResources().getColor(R.color.clr_background));
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_OPEN_IMAGE && resultCode == Activity.RESULT_OK) {
            InputStream stream = null;
            try {
                stream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                ArticleEditActivity.this.bitmap = bitmap;
                //TODO obtener  media type
                ArticleEditActivity.this.media_type = "";//data.getType();
                ((ImageView) findViewById(R.id.img_edit_image)).setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (stream != null) try { stream.close();} catch (IOException e){ }
            }
        }
    }
}
