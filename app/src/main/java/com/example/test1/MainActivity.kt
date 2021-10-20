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

    var flag = false
    /*
    生成処理
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        ビュー バインディングとは、ビューを操作するコードを簡単に記述できる機能
        ビュー バインディングは findViewById の後継
        ビュー バインディングクラスはres/layout毎に自動生成
        クラス名称は レイアウト名が  xxx_yyy.xml の場合は XxxYyyBingingとなる

        ビュー バインディングには、findViewById を使用するよりも大きなメリットがあります。
            null の安全性
            型の安全性

        bindingのイニシャライズ
        Binding Classに含まれる静的 inflate() メソッドを呼び出す
        */
        binding = ActivityMainBinding.inflate(layoutInflater)

        /*
        バインディングを使っていない場合は
        setContentView(R.layout.activity_main)
         */
        // root view への参照を取得
        val view = binding.root
        setContentView(view)

        //TTS初期化
        textToSpeech = TextToSpeech(this, this)

        binding.messageTextView.text = "A button"

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

    fun changeTextView(view: android.view.View) {
        if(flag){
            binding.messageTextView.text = "Hello"
            SpeechText("はろー")
            flag = false

        }else{
            binding.messageTextView.text = "World"
            SpeechText("わーるど")
            flag = true
        }
    }
}