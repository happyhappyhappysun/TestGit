package com.example.test;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.annotation.ElementType;
import java.util.ArrayList;

public class SaxHelper extends DefaultHandler {
    private Person person;
    private ArrayList<Person> list;
    //当前解析的元素标签
    private String tagName =null;

    /*
        读到文档开始标志时触发，完成初始化动作
     */
    @Override
    public void startDocument() throws SAXException {
        this.list = new ArrayList<>();
        Log.i("SAX", "读取到文档头,开始解析xml");
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.i("SAX", "读取到文档尾,xml解析结束");
    }

    /*
         读到一个开始标签时调用,第二个参数为标签名,最后一个参数为属性数组
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(localName.equals("person")){
            person = new Person();
            person.setId(Integer.parseInt(attributes.getValue("id")));
            Log.i("SAX", "开始处理person元素~");
        }
        //把当前标签赋值
        this.tagName = localName;
    }

    /*
        处理元素结束时触发,这里将对象添加到结合中
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(localName.equals("person")){
            this.list.add(person);
            person = null;
            Log.i("SAX", "处理person元素结束~");
        }
        this.tagName = null;
    }

    /*
        读到到内容,第一个参数为字符串内容,后面依次为起始位置与长度
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(this.tagName != null){
            //标签内容
            String data = new String(ch,start,length);
            if(this.tagName.equals("name")){
                this.person.setName(data);
                Log.i("SAX", "处理name元素内容");
            }else if(this.tagName.equals("age")){
                this.person.setAge(data);
                Log.i("SAX", "处理age元素内容");
            }
        }
    }

    /*
        获取文档中内容
     */
    public ArrayList<Person> getPersons(){
        return this.list;
    }
}
