package edu.towson.cis.cosc455.thoward.project1

import java.util

import edu.towson.cis.cosc455.thoward.project1.helpers.Constants
import edu.towson.cis.cosc455.thoward.project1.traits.Declaration

import scala.collection.mutable.{ArrayBuffer, ListBuffer, Stack}

class MySemanticAnalyzer {
  var convertedStack = Stack[String]()
  var parseStack = Stack[String]()
  var variableList= Stack[Declaration]()
  var varInt: Int = 0
  var scope: Int = 0
  var dec: Declaration = new Declaration {
    override var variable: String = _
    override var value: String = _
    //override var level: Int = _
  }

  var next: String = ""
  def process():String ={
    parseStack = Compiler.Parser.parseStack.reverse
    convert()
    convertedStack = convertedStack.reverse
    convertedStack.mkString
  }

  def convert(): Unit ={
    while(!parseStack.isEmpty){
      next = parseStack.pop()
      next match {
        case Constants.DOCB => convertedStack.push("<html>")
        case Constants.DOCE => convertedStack.push("</html>")
        case Constants.PARAB => convertedStack.push("<p>")
        case Constants.PARAE => convertedStack.push("</p>")
        case Constants.NEWLINE => convertedStack.push("<br>")
        case Constants.DEFB => {
          dec.variable = ""
          dec.value =""
          dec.variable = parseStack.pop().trim()
          parseStack.pop()
          dec.value = parseStack.pop().trim()
          parseStack.pop()
          variableList.push(dec)
        }
        case Constants.USEB => {
          variableUse(parseStack.pop())
          parseStack.pop()
        }
        case Constants.TITLEB => {
          convertedStack.push("<title>")
          next = parseStack.pop()
          convertedStack.push(next)
          convertedStack.push("</title>")
          parseStack.pop()
        }
        case Constants.HEADING => {
          convertedStack.push("<h1>")
          next = parseStack.pop()
          convertedStack.push(next)
          convertedStack.push("</h1>")
        }
        case Constants.LINKB => {
          val alt = parseStack.pop()
          parseStack.pop()
          parseStack.pop()
          convertedStack.push("<a href = \"")
          next = parseStack.pop()
          convertedStack.push(next)
          convertedStack.push("\">")
          convertedStack.push(alt)
          convertedStack.push(" </a>")
          parseStack.pop()
        }
        case Constants.IMAGEB => {
          val alt = parseStack.pop()
          parseStack.pop()
          parseStack.pop()
          convertedStack.push("<img src =\"")
          next = parseStack.pop()
          convertedStack.push(next)
          convertedStack.push("\" alt=\"")
          convertedStack.push(alt)
          convertedStack.push("\">")
          parseStack.pop()
        }
        case Constants.LISTITEM => {
          convertedStack.push("<li>")
          next = parseStack.pop()
          convertedStack.push(next)
          next = parseStack.top
          if (next.equalsIgnoreCase(Constants.USEB)) {
            parseStack.pop()
            variableUse(parseStack.pop())
            parseStack.pop()
          }
          convertedStack.push("</li>")
        }
        case Constants.BOLD => {
          convertedStack.push("<b>")
          next = parseStack.pop()
          convertedStack.push(next)
          parseStack.pop()
          convertedStack.push("</b>")
        }
        case _ => convertedStack.push(next)
      }
    }
  }

  def variableUse(useVar:String): Unit ={

    if(variableList.exists(_.variable.equals(useVar))){
      val scopingList: Stack[Declaration] = variableList.filter(_.variable.equals(useVar))
      if(scopingList.length == 1){
        dec = scopingList.top
      }
      else if(scopingList.length > 1){

      }
    }else{
      println("Static semantic error: " + useVar + " is not defined")
      System.exit(1)
    }
    if(dec.variable.trim().equals(useVar)) {
      convertedStack.push(dec.value + " ")
    }
  }
}
