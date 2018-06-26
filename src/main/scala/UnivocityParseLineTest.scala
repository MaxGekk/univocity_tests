import com.univocity.parsers.csv.{CsvParser, CsvParserSettings, UnescapedQuoteHandling}

object UnivocityParseLineTest {
  def asParserSettings: CsvParserSettings = {
    val settings = new CsvParserSettings()
    val format = settings.getFormat
    format.setDelimiter(',')
    format.setQuote('\"')
    format.setQuoteEscape('\\')
    format.setComment('\u0000')
    settings.setIgnoreLeadingWhitespaces(false)
    settings.setIgnoreTrailingWhitespaces(false)
    settings.setReadInputOnSeparateThread(false)
    settings.setInputBufferSize(128)
    settings.setMaxColumns(20480)
    settings.setNullValue("")
    settings.setEmptyValue("")
    settings.setMaxCharsPerColumn(-1)
    settings.setUnescapedQuoteHandling(UnescapedQuoteHandling.STOP_AT_DELIMITER)
    settings
  }

  def main(args: Array[String]): Unit = {
    val parserSetting = asParserSettings
    val tokenIndexArr = Seq.empty[java.lang.Integer]
    parserSetting.selectIndexes(tokenIndexArr: _*)
    val tokenizer = new CsvParser(parserSetting)

    (0 until 5).foreach { i =>
      val line = i.toString
      val parsed = tokenizer.parseLine(line)

      parsed match {
        case null => println(s""""$line" -> null""")
        case s: Array[java.lang.String] =>
          println(s""""$line" -> ${s.mkString("[", ",", "]")}""")
      }
    }
  }
}
