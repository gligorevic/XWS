import React, { useState } from "react";
import AllUserCars from "./AllUserCars";
import AddCar from "./AddCar";

export default function CarPage() {
  const [addCarDialogOpened, setAddCarDialogOpened] = useState(false);

  return (
    <div>
      <AllUserCars setAddCarDialogOpened={setAddCarDialogOpened} />
      <AddCar open={addCarDialogOpened} setOpen={setAddCarDialogOpened} />
    </div>
  );
}
