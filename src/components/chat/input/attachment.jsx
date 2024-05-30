import { AttachmentIcon } from '../../../svg'

export default function Attachment() {
  return (
    <li className="relative">
        <button className="btn" type='button'>
            <AttachmentIcon className="dark:fill-dark_svg_1"/>
        </button>
    </li>
  )
}
