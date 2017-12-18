package com.jredic.command.sub;

import com.jredic.util.Strings;
import java.util.ArrayList;
import java.util.List;

/**
 * The build class for options of Command 'SET'.
 * <p>
 * see {@link com.jredic.command.StringCommand#SET_OPTIONS}
 * see <a href="https://redis.io/commands/set#options">Redis Set Command Options</a>.
 *
 * @author David.W
 */
public class SetOptionBuilder {

    public static final String OPTION_EX = "EX";
    public static final String OPTION_PX = "PX";
    public static final String OPTION_NX = "NX";
    public static final String OPTION_XX = "XX";

    private Integer ex;
    private Long px;
    private String x;

    public SetOptionBuilder ex(int seconds){
        this.ex = seconds;
        return this;
    }

    public SetOptionBuilder px(long milliseconds){
        this.px = milliseconds;
        return this;
    }

    public SetOptionBuilder nx(){
        this.x = OPTION_NX;
        return this;
    }

    public SetOptionBuilder xx(){
        this.x = OPTION_XX;
        return this;
    }

    public List<String> build(){
        List<String> options = new ArrayList<>();
        //ex
        if(ex != null){
            options.add(OPTION_EX);
            options.add(ex.toString());
        }
        //px
        if(px != null){
            options.add(OPTION_PX);
            options.add(px.toString());
        }
        //nx/xx
        if(!Strings.isNullOrEmpty(x)){
            options.add(x);
        }
        return options;
    }

}
