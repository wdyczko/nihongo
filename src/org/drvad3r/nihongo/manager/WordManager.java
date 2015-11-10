package org.drvad3r.nihongo.manager;

import org.drvad3r.nihongo.model.Word;
import org.drvad3r.nihongo.model.list.WordList;

import java.util.ArrayList;
import java.util.Random;

/**
 * Author: Wiktor
 * Creation: 2015-10-28
 */
public class WordManager {
    private WordList wordList;
    private ArrayList<Integer> wordsPassed;
    private Word current;
    private int index;

    public WordManager(WordList wordList) {
        this.wordList = wordList;
        wordsPassed = new ArrayList<>();
        current = new Word();
    }

    public Word randWord() {
        Random random = new Random();
        index = -1;
        do {
            index = random.nextInt(getWordsListSize());
        } while (wordsPassed.contains(index));
        wordsPassed.add(index);
        current = wordList.getWords().get(index);
        return current;
    }

    public Word getCurrent() {
        return this.current;
    }

    public int getPassedSize()
    {
        return (wordsPassed != null) ? wordsPassed.size() : 0;
    }

    public int getWordsListSize()
    {
        return (wordList != null) ? wordList.getWords().size() : 0;
    }

    public void unpassLastIndex()
    {
        wordsPassed.remove(new Integer(index));
    }
}
