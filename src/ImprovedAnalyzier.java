import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class ImprovedAnalyzier extends Analyzer {

    public static final CharArraySet STOP_WORDS_SET;
    public static final CharArraySet ENGLISH_STOP_WORDS_SET;
    private int maxTokenLength;
    private final CharArraySet stopwords;

    public ImprovedAnalyzier(CharArraySet stopwords) {
        this.stopwords = stopwords == null ? CharArraySet.EMPTY_SET : CharArraySet.unmodifiableSet(CharArraySet.copy(stopwords));
        this.maxTokenLength = 255;
    }

    public ImprovedAnalyzier() {
        this(STOP_WORDS_SET);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        final StandardTokenizer src = new StandardTokenizer();
        src.setMaxTokenLength(this.maxTokenLength);
        TokenStream tok = new StandardFilter(src);
        tok = new LowerCaseFilter(tok);
        tok = new StopFilter(tok, this.stopwords);
        tok = new PorterStemFilter(tok);
        return new TokenStreamComponents(src, tok) {
            protected void setReader(Reader reader) {
                src.setMaxTokenLength(ImprovedAnalyzier.this.maxTokenLength);
                super.setReader(reader);
            }
        };
    }

    @Override
    protected TokenStream normalize(String fieldName, TokenStream in) {
        return new LowerCaseFilter(new StandardFilter(in));
    }

    static {
        List<String> stopWords = Arrays.asList("a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is", "it", "no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there", "these", "they", "this", "to", "was", "will", "with");
        CharArraySet stopSet = new CharArraySet(stopWords, false);
        ENGLISH_STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
        STOP_WORDS_SET = ENGLISH_STOP_WORDS_SET;
    }

}
