import React, { useState } from "react";
import AllUserPricelists from "./AllUserPricelists";
import AddPricelist from "./AddPricelist";

export default function PricelistPage() {
  const [addPricelistDialogOpened, setAddPricelistDialogOpened] = useState(
    false
  );

  return (
    <div>
      <AllUserPricelists
        setAddPricelistDialogOpened={setAddPricelistDialogOpened}
      />
      <AddPricelist
        open={addPricelistDialogOpened}
        setOpen={setAddPricelistDialogOpened}
      />
    </div>
  );
}
