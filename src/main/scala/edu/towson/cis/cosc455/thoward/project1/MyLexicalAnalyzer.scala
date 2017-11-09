package edu.towson.cis.cosc455.thoward.project1


class MyLexicalAnalyzer extends LexicalAnalyzer{

  private var file : String= ""
  private var currentToken: List[Char] = List()
  private var nextChar:Char = 0
  private var position = 0
  private var validText : List[String] = List()
  private var lexemes : List[String] = List()

  def start(f: String): Unit = {
      initializeLexemes()
      file = f
      position = 0
      //getChar()
      //getNextToken()
  }

  override def getNextToken(): String = {
    getChar()
    currentToken = List()
    while(isSpace(nextChar)){
      getChar()
    }
    if(isStartSymbol()){
      nextChar match {
        case '=' => addChar()
        case '(' => addChar()
        case '+' => addChar()
        case '*' => addChar()
        case '#' => addChar()
        case ')' => addChar()
        case ']' => addChar()
        case '[' => addChar()
        case '!' => processToken()
        case '\\' => processToken()
        case  _ => println("what the hell")     //REMOVE THIS
      }
      checkIfValid()
    }
    else if (validText.contains(nextChar.toString())){
      processText()
    } else{
      //error
      System.exit(1);
    }

    currentToken.mkString
    //else error
  }
  def checkIfValid(): Unit ={
    if(!lookup(currentToken.mkString)){
      println("LEXICAL ERROR")
      System.exit(1)
    }
  }

  def processText(): Unit ={
    var shouldContinue: Boolean = true

    while(shouldContinue){
      if(isStartSymbol()){
        shouldContinue = false
      }
      else if (!Constants.whiteEscapes.contains(file.charAt(position)) && !Constants.leadSymbols.contains(file.charAt(position))) {
        addChar()
        getChar()
      }
      else{
        addChar()
        shouldContinue = false
      }

    }
  }
  def processToken(): Unit = {
    var shouldContinue:Boolean = true
    if (nextChar.equals('!')) {
      addChar()
      getChar()
      if (nextChar.equals('[')) {
        addChar()
      }
    }
    else if (nextChar.equals('\\')) {
      addChar()
      getChar()
      if (nextChar.equals('\\')) {
        addChar()
      }
      else {
        while (shouldContinue) {
          if (nextChar.equals('[')) {
            addChar()
            shouldContinue = false
          }
          else if(isSpace(nextChar)){
            shouldContinue = false
          }
          else {
            addChar()
            getChar()
          }
        }
      }
    }
  }

  def isStartSymbol(): Boolean ={
      Constants.leadSymbols.contains(nextChar)
  }

  override def lookup(candidateToken: String): Boolean = {
      lexemes.contains(candidateToken)
  }

  override def getChar(): Unit = {
    if (position < file.length()) {
      nextChar = file.charAt(position)
      position = position + 1
    }
  }

  override def addChar(): Unit = {
    currentToken :+= nextChar
  }

  private def isSpace(c: Char):Boolean = {
    Constants.whiteSpace.contains(c.toString())
  }

  private def initializeLexemes() = {
    validText = Constants.letters ::: Constants.numbersEtc
    lexemes = Constants.tokens

  }

}

