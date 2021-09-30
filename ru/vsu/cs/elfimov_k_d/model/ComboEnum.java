package ru.vsu.cs.elfimov_k_d.model;

public enum ComboEnum {
    KICKER("Кикер"),
    COUPLE("Пара"),
    TWOCOUPLE("Две пары"),
    SET("Сет"),
    STREET("Стрит"),
    FLESH("Флеш"),
    FULLHOUSE("Фулл-Хаус"),
    KARE("Каре"),
    STREETFLESH("Стрит-флеш"),
    ROYALFLESH("Роял-флеш");

    private String name;

    ComboEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
