package org.drvad3r.nihongo.define;

/**
 * Author: Wiktor
 * Creation date: 2015-12-05.
 */
public enum Option {
    ENGLISH(1),
    ORIGINAL(2),
    PRONOUNCE(4),
    LOCAL(8);
    private final int optionCode;

    Option(int optionCode)
    {
        this.optionCode = optionCode;
    }

    public final int getOptionCode()
    {
        return this.optionCode;
    }

    public static String combine(Option... options)
    {
        int parameter = 0;
        for (int i = 0; i < options.length; i++)
        {
            parameter |= options[i].getOptionCode();
        }
        return Integer.toString(parameter);
    }

    public static boolean isEnglish(int parameter)
    {
        return (parameter & ENGLISH.getOptionCode()) != 0;
    }

    public static boolean isOriginal(int parameter)
    {
        return (parameter & ORIGINAL.getOptionCode()) != 0;
    }

    public static boolean isPronounce(int parameter)
    {
        return (parameter & PRONOUNCE.getOptionCode()) != 0;
    }

    public static boolean isLocal(int parameter)
    {
        return (parameter & LOCAL.getOptionCode()) != 0;
    }

    public static Option toOption(int parameter)
    {
        switch (parameter)
        {
            case 1: return ENGLISH;
            case 2: return ORIGINAL;
            case 4: return PRONOUNCE;
            case 8: return LOCAL;
        }
        return null;
    }

    @Override
    public String toString()
    {
        return Integer.toString(getOptionCode());
    }
}
