from datetime import *
from functools import *
from sys import *
from itertools import accumulate, combinations, permutations
from bisect import *
from heapq import *
from collections import *
from math import *
from types import *
import random
RANDOM = random.randint(0, 10**9 + 7)
del pow
# math.ceil(x)向上取整,math.comb(n, k) 组合数C(n,k),从n个里面挑k个出来,math.isqrt(),整数开根号
# from sortedcontainers import *
input = stdin.readline
# print = stdout.write #如果输出多的话记得用快写
# setrecursionlimit (10000)

# 对浮点数进行四舍五入, 小数位不足自动补零


def rounding_num(num, x):
    num = round(num, x)
    num = "{{:.{}f}}".format(x).format(num)
    return num

def bootstrap(f, stack=[]):
    def wrappedfunc(*args, **kwargs):
        if stack:
            return f(*args, **kwargs)
        else:
            to = f(*args, **kwargs)
            while True:
                if type(to) is GeneratorType:
                    stack.append(to)
                    to = next(to)
                else:
                    stack.pop()
                    if not stack:
                        break
                    to = stack[-1].send(to)
            return to
    return wrappedfunc

# 遇到没有确定行数的输入时可以用这个来读取输入
# while True:
#     try:
#         #这里写你的输入
#         pass
#     except:
#         break


class BIT1:  # 单点修改， 区间求和, 其中单点修改和区间求和的时间复杂度都是o(log(n))
    __slots__ = "size", "tree"

    def __init__(self, n) -> None:
        self.tree = [0] * (n + 1)
        self.size = n

    def lowbit(self, x):  # 求x二进制最低位1
        return x & -x

    def add(self, x, val):  # 单点加法修改
        while x <= self.size:
            self.tree[x] += val
            x += self.lowbit(x)

    def query(self, x):  # 查询1 ~ x的数组和
        res = 0
        while x:
            res += self.tree[x]
            x -= self.lowbit(x)
        return res

    def querylr(self, l, r):  # 区间查询
        return self.query(r) - self.query(l - 1)


n = int(input())
arr = list(map(int, input().split()))
res = 0
ma = int(1e6)
t = BIT1(ma + 10)
for x in arr:
    res += t.querylr(x + 1, ma)
    t.add(x, 1)
tar1 = n * 3
tar2 = 7 * n + 1
if tar1 % 2 == res % 2:
    print('Petr')
else:
    print('Um_nik')
