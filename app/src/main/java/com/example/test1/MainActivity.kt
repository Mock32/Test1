package com.example.test1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.test1.databinding.ActivityMainBinding //手動で追加
import android.speech.tts.TextToSpeech  //手動で追加
import android.widget.Toast
import java.util.*


//: AppCompatActivityクラスを継承
//  TextToSpeech.OnInitListenerはインターフェイス
//      public static interface TextToSpeech.OnInitListener
//      abstract void	onInit(int status)
//      CTextToSpeechエンジンの初期化の完了を知らせるために呼び出されます。
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    lateinit var binding: ActivityMainBinding  //手動で追加
    private var textToSpeech: TextToSpeech? = null // 変数名に？がつくのはNULL許容型

        // ~省略~


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // bindingのイニシャライズ
        // Binding Classに含まれる静的 inflate() メソッドを呼び出す
        binding = ActivityMainBinding.inflate(layoutInflater)

        // root view への参照を取得
        val view = binding.root

        setContentView(view)

        //TTS初期化
        textToSpeech = TextToSpeech(this, this)

        var flag = false
        binding.messageTextView.text = "A button"

        binding.changeButton.setOnClickListener{

          if(flag){
              binding.messageTextView.text = "Hello"
              SpeechText("はろー")
              flag = false

          }else{
              binding.messageTextView.text = "World"
              SpeechText("わーるど")
              flag = true
          }
        };

    }

    //TTSの初期化後呼ばれる
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech?.let { tts ->
                val locale = Locale.JAPAN
                if (tts.isLanguageAvailable(locale) > TextToSpeech.LANG_AVAILABLE) {
                    tts.language = Locale.JAPAN
                } else {
                    // 言語の設定に失敗
                    Toast.makeText(this@MainActivity,"言語設定に失敗",Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Tts init 失敗
            Toast.makeText(this@MainActivity,"Tts init 失敗",Toast.LENGTH_SHORT).show()
        }
    }


    private fun SpeechText(text:String){
        textToSpeech?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"ID")
        }

    override fun onDestroy() {
        textToSpeech?.shutdown()
        super.onDestroy()
    }

}