#!/usr/bin/python3

from string import Template
import sys
import os

def generate_file(template, path, name, mapping):
    with open(template, 'r') as file:
        src = Template(file.read())
        result = src.safe_substitute(mapping)

        if not os.path.exists(path):
            os.mkdir(path)
        f = open(os.path.join(path, name), 'w')
        f.write(result)
        f.close()

        print(f'Created {name}')


if len(sys.argv) < 2:
    name = input("Enter the name of the component:\nBpk")
else:
    name = sys.argv[1]

if len(sys.argv) < 3:
    package = input("Enter the package name of the component, or leave empty for " + name.lower() + ":\n")
    if len(package) == 0:
        package = name.lower()
else:
    package = sys.argv[2]

print('')
print('Creating component structure...')
print('')

mapping = {
    'name': name,
    'package': package
}
dirname = os.path.dirname(__file__)
generate_file(os.path.join(dirname, '../templates/component/Component.kt'), os.path.join(dirname, f'../backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/{package}/'), f'Bpk{name}.kt', mapping)
generate_file(os.path.join(dirname, '../templates/component/ComponentAnnotation.kt'), os.path.join(dirname, f'../app/src/main/java/net/skyscanner/backpack/demo/components/'), f'{name}Component.kt', mapping)
generate_file(os.path.join(dirname, '../templates/component/Story.kt'), os.path.join(dirname, f'../app/src/main/java/net/skyscanner/backpack/demo/compose/'), f'{name}Story.kt', mapping)
generate_file(os.path.join(dirname, '../templates/component/Test.kt'), os.path.join(dirname, f'../app/src/androidTest/java/net/skyscanner/backpack/compose/{package}/'), f'Bpk{name}Test.kt', mapping)
generate_file(os.path.join(dirname, '../templates/component/README.md'), os.path.join(dirname, f'../docs/compose/{name}/'), 'README.md', mapping)

print('')
print('Enjoy building the component!')
