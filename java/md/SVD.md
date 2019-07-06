SVD也可以实现数据的降维，

实现的做法就是把原始数据，分解为三个小数据（矩阵），只存储那三个小矩阵。之后通过三个小矩阵进行计算，还原原始数据。


$$
A=UΣV^T
$$
A是任意矩阵（原始数据），U是酉矩阵，V也是酉矩阵。

那么，我们有

$$
\begin{aligned} \mathbf A\mathbf A^{\mathsf T} &{}= \mathbf U\mathbf\Sigma\mathbf V^{\mathsf T}\mathbf V\mathbf\Sigma^{\mathsf T}\mathbf U^{\mathsf T} = \mathbf U(\mathbf\Sigma\mathbf\Sigma^{\mathsf T})\mathbf U^{\mathsf T}\\ \mathbf A^{\mathsf T}\mathbf A &{}= \mathbf V\mathbf\Sigma^{\mathsf T}\mathbf U^{\mathsf T}\mathbf U\mathbf\Sigma\mathbf V^{\mathsf T} = \mathbf V(\mathbf\Sigma^{\mathsf T}\mathbf\Sigma)\mathbf V^{\mathsf T}\\ \end{aligned}
\\
\large 这也就是说，U 的列向量（左奇异向量），是 AA^T 的特征向量；
\\\large 同时，V 的列向量（右奇异向量），是 AA^T 的特征向量；
\\\large 另一方面，M的奇异值（Σ的非零对角元素）则是 AA^T 或者 A^TA 的非零特征值的平方根。
$$

