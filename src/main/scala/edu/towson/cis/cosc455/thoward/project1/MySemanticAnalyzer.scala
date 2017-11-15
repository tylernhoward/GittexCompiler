package edu.towson.cis.cosc455.thoward.project1

import java.util

import edu.towson.cis.cosc455.thoward.project1.helpers.{Constants, Declaration}

import scala.collection.mutable.Stack

class MySemanticAnalyzer {
  var convertedStack = Stack[String]()
  var parseStack = Stack[String]()
  var variableList = Stack[Declaration]()
  var scope: Int = 0

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
        case Constants.PARAB => {
          convertedStack.push("<p>")
          scope = 1
        }
        case Constants.PARAE => {
          convertedStack.push("</p>")
          scope = 0
        }
        case Constants.DEFB => variableDefine()
        case Constants.USEB => variableUse(parseStack.pop())
        case Constants.NEWLINE => convertedStack.push("<br>")
        case Constants.BOLD => makeBoldItem()
        case Constants.TITLEB => makeTitle()
        case Constants.HEADING => makeHeading()
        case Constants.LINKB => makeLink()
        case Constants.IMAGEB => makeImage()
        case Constants.LISTITEM => makeListItem()
        case _ => convertedStack.push(next)
      }
    }
  }
  def makeTitle(): Unit ={
    convertedStack.push("<title>")
    next = parseStack.pop()
    convertedStack.push(next)
    convertedStack.push("</title>")
    next = parseStack.pop()
  }
  def makeHeading(): Unit ={
    convertedStack.push("<h1>")
    next = parseStack.pop()
    if(next.equals(Constants.USEB)) {
      parseStack.pop()
      variableUse(parseStack.pop())
      next = parseStack.pop()
    }else convertedStack.push(next)
    convertedStack.push("</h1>")
  }
  def makeBoldItem(): Unit ={
    convertedStack.push("<b>")
    next = parseStack.pop()
    if(next.equals(Constants.USEB)) {
      parseStack.pop()
      variableUse(parseStack.pop())
      parseStack.pop()
    }
    else convertedStack.push(next)
    next = parseStack.pop()
    convertedStack.push("</b>")
  }
  def makeLink(): Unit ={
    val alt = parseStack.pop()
    parseStack.pop()
    parseStack.pop()
    convertedStack.push("<a href = \"")
    next = parseStack.pop()
    convertedStack.push(next)
    convertedStack.push("\">")
    convertedStack.push(alt)
    convertedStack.push(" </a>")
    next = parseStack.pop()
  }
  def makeImage(): Unit ={
    val alt = parseStack.pop()
    parseStack.pop()
    parseStack.pop()
    convertedStack.push("<img src =\"")
    next = parseStack.pop()
    convertedStack.push(next)
    convertedStack.push("\" alt=\"")
    convertedStack.push(alt)
    convertedStack.push("\">")
    next = parseStack.pop()
  }
  def makeListItem(): Unit ={
    convertedStack.push("<li>")
    next = parseStack.pop()
    convertedStack.push(next)
    next = parseStack.top
    if (next.equalsIgnoreCase(Constants.USEB)) {
      parseStack.pop()
      variableUse(parseStack.pop())
    }
    else if (next.equals(Constants.BOLD)) {
      makeBoldItem()
    }
    else if (next.equals(Constants.LINKB)) {
      makeLink()
    }
    convertedStack.push("</li>")

  }

  def variableDefine(): Unit ={
    val name = parseStack.pop().trim()
    parseStack.pop()
    val value = parseStack.pop().trim()
    parseStack.pop()
    variableList.push(new Declaration(name,value,scope))
  }

  def variableUse(useVar:String): Unit = {
    if (variableList.length == 1 && variableList.top.variable.equals(useVar)){
      convertedStack.push(variableList.top.value + " ")
    }
    else if (variableList.length > 1){
      resolveScope(useVar)
    }
    else{
      println("Static semantic error:\nNo declaration found for: " + useVar)
      System.exit(1)
    }
    next = parseStack.pop()
  }

  def resolveScope(useVar: String): Unit ={
    var isFound: Boolean = false
    var tempStack = Stack[Declaration]()
    while (!isFound && variableList.length != 0) {
      tempStack.push(variableList.pop())
      if (tempStack.top.variable.equals(useVar) && tempStack.top.level == scope) {
        isFound = true
        val foundVar: Declaration = new Declaration(tempStack.top.variable, tempStack.top.value, tempStack.top.level)
        convertedStack.push(foundVar.value + " ")
      }
      else {
        isFound = false
      }
    }
    while (!tempStack.isEmpty) {
      variableList.push(tempStack.pop())
    }
  }
}
