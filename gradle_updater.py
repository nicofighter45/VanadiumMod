import requests
import json
import os


def get_latest_version_info(mod_id):
    api_url = f"https://api.modrinth.com/v2/project/{mod_id}/version"
    params = {
        'loaders': json.dumps(["fabric"]),
        'game_versions': json.dumps([mc_version])
    }
    answer = requests.get(api_url, params=params)
    if answer.status_code == 200:
        versions = answer.json()
        for version in versions:
            if "fabric" in version['loaders'] and mc_version in version['game_versions']:
                return version
    return None


mc_version = "1.20.1"
gradle_properties_path = os.getcwd() + "\gradle.properties"
mod_names = ["architectury-api", "cloth-config", "distanthorizons", "indium", "iris", "lithium", "modmenu", "rei",
             "sodium"]
modrinth_versions = []

for name in mod_names:
    infos = get_latest_version_info(name)
    if infos is not None:
        modrinth_versions.append(infos['version_number'])
    else:
        print(name)

lines = open(gradle_properties_path, "r").readlines()
file = open(gradle_properties_path, "w")
for line in lines[:len(lines) - len(mod_names)]:
    file.write(line)
for i in range(len(mod_names)):
    line = lines[len(lines) - len(mod_names) + i]
    file.write(line.split("=")[0] + "= " + modrinth_versions[i])
    if i != len(mod_names) - 1:
        file.write("\n")
