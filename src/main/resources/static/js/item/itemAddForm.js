const radioB = document.getElementById("B");
const radioL = document.getElementById("L");
let target = null;

radioB.addEventListener("click", () => {
  whoisChecked();
});

radioL.addEventListener("click", () => {
  whoisChecked();
});

function whoisChecked() {
  if (radioB.checked) {
    radioBIsOn();
    radioLIsOff();
  } else {
    radioLIsOn();
    radioBIsOff();
  }
}

function radioBIsOn() {
  target = document.getElementById("optionForBook");
  target.classList.remove("hidden");
}

function radioBIsOff() {
  target = document.getElementById("optionForBook");
  target.classList.add("hidden");
}

function radioLIsOn() {
  target = document.getElementById("optionForLecture");
  target.classList.remove("hidden");
}

function radioLIsOff() {
  target = document.getElementById("optionForLecture");
  target.classList.add("hidden");
}
