package edu.towson.cis.cosc455.thoward.project1


class MyLexicalAnalyzer extends LexicalAnalyzer{

  private var file : String= ""
  private var currentToken: List[Char] = List()
  private var nextChar:Char = 0
  private var position = 0
  private var lexemes : List[String] = List()


  def start(f: String) :Unit = {
      initializeLexemes()
      file = f
      position = 0
      //getChar()
      //getNextToken()
  }

  override def getNextToken(): String = {
    currentToken = List()
    var tokenDiscovered: Boolean = false
    var shouldContinue: Boolean = true

    while(!tokenDiscovered){
      var c = getChar()
      if(Constants.leadSymbols.contains(c)){ //potential start of a token
        tokenDiscovered = true
        if(c == '\\' || c == '!'){
          addChar()
          c = getChar()
          if(c == '\\'){
            return currentToken.mkString
          }
          else{
            while(shouldContinue){
              if(c == '['){
                addChar()
                shouldContinue = false
              } else
              if(isSpace(c)) {
                shouldContinue = false
              } else{
                addChar()
                c = getChar()
              }
            }
          }
        }
        if (currentToken.length == 0){
          addChar()
        }

        if(lookup(currentToken.mkString)){
          return currentToken.mkString
        }
        else{
          println(currentToken.mkString + " is not valid")
        }
      }
    }
    return " " //This is sloppy

  }

  override def lookup(candidateToken: String): Boolean = {
    Constants.tokens.contains(candidateToken.toUpperCase())
  }

  override def getChar(): Char = {
    if (position < file.length()) {
      nextChar = file.charAt(position)
      position = position + 1
    }
    nextChar
  }

  override def addChar(): Unit = {
    currentToken :+= nextChar
  }

  private def isSpace(c: Char):Boolean = {
    Constants.whiteSpace.contains(c.toString())
  }

  private def initializeLexemes() = {
    lexemes = Constants.tokens ::: Constants.letters ::: Constants.numbersEtc

  }

}

