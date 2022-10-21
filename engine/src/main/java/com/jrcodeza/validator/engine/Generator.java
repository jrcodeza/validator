package com.jrcodeza.validator.engine;

import com.jrcodeza.validator.api.SimpleValidator;
import com.jrcodeza.validator.api.ValidationError;
import com.jrcodeza.validator.predefined.NotNullValidator;
import com.squareup.javapoet.*;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    public static void main(String[] args) {
        new Generator().generate();
    }

    public void generate() {
        var clazz = Sample.class; // traversal here now only flat
        var classBuilder = TypeSpec
                .classBuilder(clazz.getSimpleName() + "Validator")
                .addField(
                        ParameterizedTypeName.get(
                                ClassName.get(List.class),
                                ClassName.get(SimpleValidator.class)
                        ),
                        "simpleValidators",
                        Modifier.PRIVATE, Modifier.FINAL
                )
                .addModifiers(Modifier.PUBLIC);

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("validate")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(
                        clazz,
                        "input"
                )
                .returns(ParameterizedTypeName.get(
                        ClassName.get(List.class),
                        ClassName.get(ValidationError.class)
                ));

        var constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(
                        ParameterizedTypeName.get(
                                ClassName.get(List.class),
                                ClassName.get(SimpleValidator.class)
                        ),
                        "simpleValidators")
                .addStatement("this.simpleValidators = simpleValidators");

        ReflectionUtils.doWithFields(
                clazz,
                field -> {
                    methodBuilder
                            .addStatement("$T<$T> validationErrors = new $T<>()", List.class, ValidationError.class, ArrayList.class)
                            .beginControlFlow("for ($T simpleValidator : simpleValidators)", SimpleValidator.class);
                    for (Annotation annotation : field.getAnnotations()) {
                        methodBuilder
                                .beginControlFlow("if (simpleValidator.supportsAnnotation($T.class))", annotation.annotationType())
                                .addStatement("validationErrors.addAll(simpleValidator.validate(input.get%s(), null))".formatted(StringUtils.capitalize(field.getName())))
                                .endControlFlow();
                    }
                    methodBuilder
                            .endControlFlow()
                            .addStatement("return validationErrors");
                },
                field -> field.getAnnotations() != null && field.getAnnotations().length != 0
        );

        TypeSpec mappingClass = classBuilder
                .addMethod(constructor.build())
                .addMethod(methodBuilder.build())
                .build();

        // generate .java file
        try {
            JavaFile.builder("sk.jrcodeza.output", mappingClass)
                    .build()
                    .writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
