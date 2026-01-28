import CuisineList from "./CuisineList";
import { getCuisinesByRegion } from "@/app/lib/api";

type Props = {
  params: {
    region: string;
  };
};

export default async function RegionPage(props: Props) {
  const { region } = await props.params;
  const decodedRegion = decodeURIComponent(region);

  const cuisines = await getCuisinesByRegion(decodedRegion);

  return (
    <div>
      <h1>Cuisines in {decodedRegion}</h1>
      <CuisineList cuisines={cuisines} region={decodedRegion} />
    </div>
  );
}
