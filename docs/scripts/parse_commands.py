"""Parse help from the comments in the source code. This is used to generate a help section in-game, and to generate markdown documentation."""
import javalang
from pathlib import Path
import re
from argparse import ArgumentParser

parser = ArgumentParser()
parser.add_argument("--format", choices=["java", "md"], default="java")
args = parser.parse_args()

root = Path(__file__).resolve().parent.parent.parent

modules = ["AdminCommand", "GMCommand", "PlayerCommand"]

data = {}
for module in modules:
    commands = []
    tree = javalang.parse.parse(
        (root / f"src/client/player/commands/{module}.java").read_text()
    )
    for path, node in tree.filter(javalang.tree.ClassDeclaration):
        if node.name == module:
            continue
        if not node.documentation:
            print(node.name, "missing documentation")
        doc = re.search(r"/\*\*(.*)\*", node.documentation).group(1).strip()
        commands.append(doc)
    data[module] = commands

if args.format == "java":
    # print out the relevant help for each module, formatting can be bad
    # because we'll just format it using prettier
    for module in modules:
        print(f"\n\nhelp for {module}")
        method = """public static class Help extends CommandExecute {
            @Override
            public boolean execute(Client c, String[] splitted) {
                final Player p = c.getPlayer();
        """
        for doc in data[module]:
            method += f'p.dropMessage(5, "{doc}");'
        method += "return true;} }"
        print(method)

elif args.format == "md":
    print("# Command Help\n")
    for module in reversed(modules):
        print(f"## {module}\n")
        # now we're going to build tables for commands
        print("command | description")
        print("-|-")
        for line in data[module]:
            delimited = line.split(" - ")
            command = delimited[0]
            description = "-".join(delimited[1:])
            print(f"{command}|{description}")
        print("\n")
