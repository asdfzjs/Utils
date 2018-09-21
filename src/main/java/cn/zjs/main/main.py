import unittest

def is_digit(d):
    return d>='0' and d<='9'

def sum_digits(digits):
     num = 0 
     for j in range(0,len(digits)):
       num = num + digits[j] * 10 ** (len(digits) - j -1)
     return num

def calculate(str):
    has_digit_state=False
    end_space_state=False
    start_space_state=False
    new_section_state=True
    init_state=True
    digits=[]
    output = 0
    section_count=0
    for i in range(0, len(str)):
       x = str[i]
       if is_digit(x):
           if new_section_state or has_digit_state or start_space_state:
               digits.append(int(x))
               new_section_state=False
               has_digit_state=True
               start_space_state=False
           else:
               raise Exception("invalid format")
       elif x == ' ':
           if init_state:
               raise Exception("invalid format")
           if start_space_state or end_space_state:
              continue
           elif has_digit_state:
               end_space_state = True
               has_digit_state = False
               continue
           elif new_section_state:
               start_space_state=True
               new_section_state=False
           else:
               raise Exception("invalid format")
       elif x == '.':
          if init_state:
              raise Exception("invalid format")
          if has_digit_state or end_space_state:
              output = output << 8
              num = sum_digits(digits)
              output = output + num
              if num > 255:
                raise Exception("invalid format")
              digits=[]
              new_section_state=True
              has_digit_state=False
              end_space_state=False
              section_count += 1
          else:
             raise Exception("invalid format")
       else:
           raise Exception("invalid format")
       init_state=False

    if section_count != 3:
              raise Exception("invalid format")
    if start_space_state or end_space_state or new_section_state:
              raise Exception("invalid format")
    output = output << 8
    num = sum_digits(digits)
    output = output + num
    if num > 255:
        raise Exception("invalid format")
          
    return output    


class Test(unittest.TestCase):
     def test_normal(self):
          self.assertEquals(calculate("172.168.5.1"), 2896692481)

     def test_space(self):
         self.assertEquals(calculate("172 .168.5.1"), 2896692481)
         self.assertEquals(calculate("172  .168.5.1"), 2896692481) 
         self.assertEquals(calculate("172. 168.5.1"), 2896692481)
         self.assertEquals(calculate("172 . 168.5.1"), 2896692481)
         
         self.assertEquals(calculate("172.168 .5.1"), 2896692481)
         self.assertEquals(calculate("172.168  .5.1"), 2896692481) 
         self.assertEquals(calculate("172.168. 5.1"), 2896692481)
         self.assertEquals(calculate("172.168 . 5.1"), 2896692481)

         self.assertEquals(calculate("172.168.5 .1"), 2896692481)
         self.assertEquals(calculate("172.168.5  .1"), 2896692481) 
         self.assertEquals(calculate("172.168.5. 1"), 2896692481)
         self.assertEquals(calculate("172.168.5 . 1"), 2896692481)

         self.assertEquals(calculate("172 . 168 . 5 . 1"), 2896692481)

     def test_exception(self):
        with self.assertRaises(Exception):
            calculate("256.168.5.1")
        with self.assertRaises(Exception):
            calculate("172.1 68.5.1")
        with self.assertRaises(Exception):
            calculate(" 172.168.5.1")
        with self.assertRaises(Exception):
            calculate("172.168.5.1 ")
        with self.assertRaises(Exception):
            calculate("172.168.5.")
        with self.assertRaises(Exception):
            calculate("172.168.5")
        with self.assertRaises(Exception):
            calculate("172")

if __name__ == '__main__':
    unittest.main()

