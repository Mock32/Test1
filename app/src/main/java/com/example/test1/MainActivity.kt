package com.example.test1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.test1.databinding.ActivityMainBinding //手動で追加



class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding  //手動で追加

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // bindingのイニシャライズ
        // Binding Classに含まれる静的 inflate() メソッドを呼び出す
        binding = ActivityMainBinding.inflate(layoutInflater)

        // root view への参照を取得
        val view = binding.root

        setContentView(view)

        var flag = false
        binding.messageTextView.text = "A button"

        binding.changeButton.setOnClickListener{

          if(flag){
              binding.messageTextView.text = "Hello"
              flag = false

          }else{
              binding.messageTextView.text = "World"
              flag = true
          }
        };

    }
}