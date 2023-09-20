# ������������ ������� � �������������� ���������������

## ���� �������

���� ����� ������� � ������������ ������� ��������� �������������� ���������������� �� Java ����� ������ ������������ �������. �� �������� ������ �������� �������, ����������� ���������� �������, � ��� ������, ������� ����� ����������� ������ �������� �������. ����� ���������� �������, �������� ����� ����� �������� � �������.

## ����������� ����������

- ���� ����������������: Java
- ������ JDK: 8 ��� ����

## �������������� ����������

1. **������������� �������**: ������� ������ ����� ����� ��������, ��������, 1 000 000 ���������. �������� ������� ������ ���� ��������� ���������� �������.

2. **�������� �������**: ������� ��� ������ (`Thread`), ������ �� ������� ����� ����������� ���� �������� �������.

3. **������������**: ������ ����� ��������� ������ �������� �������, ������ ����� � ������ ��������.

4. **������������� �������**: ������������ ����� `join()` ��� ����, ����� �������� ����� ������ ���������� �������������� �������.

5. **����� ����������**: ����� ���������� ���� �������, ������� ���������� ������� ������ � ������� �������� ����� � �������.

## ��������� ��������� �������

```text
.
??? README.md
??? src
?   ??? main
?       ??? java
?           ??? ArraySummation
?               ??? Main.java
?               ??? SumThread.java
??? pom.xml  (���� ������������ Maven)
```

### ������� �������� �������

- `Main`: �������� �����, ������� �������� ����� `main`. � ���� ������ ����� ��������������� ������ � ������� ������.

- `SumThread`: �����, ������� ��������� `Thread` ��� ��������� `Runnable`. �������� ������ ������������ ��������� �������.

## ��������� ���� ����������

1. ������� � ��������� ������ ���������� �������.
2. �������� ����� `SumThread`, ������� ��������� ����� �������.
3. � ������ `Main` ������� � ��������� ��� ������, ������� �� ��������������� ����� ������� ��� ������������.
4. ������������ `join()` ��� �������� ���������� �������.
5. ������� ���������� � ������� �������� �����.

## �������� ����������

- ��� ������������� � ������� ��������� ������ ������������.
- ��� ������ ��������� ��������� ���� ����� �������.
- �������� ����� ��������� ��������� � ������� ����� ���������� ���� �������.