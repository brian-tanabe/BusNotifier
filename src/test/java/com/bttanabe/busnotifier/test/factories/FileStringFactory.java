package com.bttanabe.busnotifier.test.factories;

import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * Created by Brian on 4/15/16.
 */
public class FileStringFactory implements FactoryBean<String> {

    @Setter(onMethod = @__({@Autowired}))
    private File inputFile;

    @Override
    public String getObject() throws Exception {
        return FileUtils.readFileToString(inputFile);
    }

    @Override
    public Class<?> getObjectType() {
        return String.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}