from shutil import copyfile
from glob import glob
import os

fruits_label_parent_filepath = "C:/Users/shaun/Documents/GitHub/CUTN-MobileApp/dataset/train/Fruit"
veg_label_parent_filepath = "C:/Users/shaun/Documents/GitHub/CUTN-MobileApp/dataset/train/Vegetables"
dataset_filepath = "C:/Users/shaun/Documents/GitHub/CUTN-MobileApp/dataset"

fruits_label_filepath = glob(fruits_label_parent_filepath + "/*/")
veg_label_filepath = glob(veg_label_parent_filepath + "/*/")

fruit_labels = []
veg_labels = []

all_img_dict = {}


for veg_dir in veg_label_filepath:
  veg_dir = veg_dir.split("\\")
  veg_labels.append(veg_dir[1])

for fruit_dir in fruits_label_filepath:
  fruit_dir = fruit_dir.split("\\")
  fruit_labels.append(fruit_dir[1])

dataset_child_filepath = glob(dataset_filepath + "/*/")
# print(dataset_child_filepath)
for child_dataset in dataset_child_filepath:
  # product directory
  # print(child_dataset)
  product_parent_filepath = glob(child_dataset + "/*/")
  for product_filepath in product_parent_filepath:
    
    if ("Packages" in product_filepath):
      if 'Milk' in all_img_dict:
        all_img_dict['Milk'] += glob(product_filepath + '/**/*.jpg', recursive=True)
      else:
        all_img_dict['Milk'] = glob(product_filepath + '/**/*.jpg', recursive=True)
    elif ("Fruit" in product_filepath):
      for fruit in fruit_labels:
        fruit_filepath = product_filepath + fruit
        if fruit in all_img_dict:
          all_img_dict[fruit] += glob(fruit_filepath + '/**/*.jpg', recursive=True)
        else: 
          all_img_dict[fruit] = glob(fruit_filepath + '/**/*.jpg', recursive=True)
    else:
      for veg in veg_labels:
        veg_filepath = product_filepath + veg
        if veg in all_img_dict:
          all_img_dict[veg] += glob(veg_filepath + '/**/*.jpg', recursive=True)
        else: 
          all_img_dict[veg] = glob(veg_filepath + '/**/*.jpg', recursive=True)

dst_parent = "C:/Users/shaun/Documents/GitHub/CUTN-MobileApp/dataset/parent/"
for product_key in all_img_dict:
  product_img_list = all_img_dict[product_key]
  for i in range(len(product_img_list)):
    dst = dst_parent + product_key + "/" 
    os.makedirs(dst, exist_ok=True) 
    dst +=  product_key + str(i) + ".jpg"
    # print(dst)
    copyfile(product_img_list[i], dst)
