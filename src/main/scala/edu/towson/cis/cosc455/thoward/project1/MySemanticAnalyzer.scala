package edu.towson.cis.cosc455.thoward.project1

import scala.collection.mutable.Stack

class MySemanticAnalyzer {
  var convertedStack = Stack[String]()
  var parseStack = Stack[String]()

  var next: String = ""
  def process(): Unit ={
    parseStack = Compiler.Parser.parseStack.reverse
    convert()
    convertedStack = convertedStack.reverse
    println(convertedStack.mkString)

  }
  def convert(): Unit ={
    while(!parseStack.isEmpty){
      next = parseStack.pop()
      if (next.equalsIgnoreCase(Constants.DOCB)) {
        convertedStack.push("<html>")
      }
      else if (next.equalsIgnoreCase(Constants.DOCE)) {
        convertedStack.push("</html>")
      }
      else if (next.equalsIgnoreCase(Constants.PARAB)) {
        convertedStack.push("<p>")
      }
      else if (next.equalsIgnoreCase(Constants.PARAE)) {
        convertedStack.push("</p>")
      }
      else if (next.equalsIgnoreCase(Constants.TITLEB)) {
        convertedStack.push("<title>")
        next = parseStack.pop()
        convertedStack.push(next)
        convertedStack.push("</title>")
        parseStack.pop()
      }
      else if (next.equalsIgnoreCase(Constants.LINKB)) {
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
      else if (next.equalsIgnoreCase(Constants.IMAGEB)) {
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
      else if (next.equalsIgnoreCase(Constants.LISTITEM)) {
        convertedStack.push("<li>")
        next = parseStack.pop()
        convertedStack.push(next)
        convertedStack.push("</li>")
      }
      else if (next.equalsIgnoreCase(Constants.HEADING)) {
        convertedStack.push("<h1>")
        next = parseStack.pop()
        convertedStack.push(next)
        convertedStack.push("</h1>")
      }
      else if (next.equalsIgnoreCase(Constants.NEWLINE)) {
        convertedStack.push("<br>")
      }
      else {
        convertedStack.push(next)
      }
    }
  }
}
